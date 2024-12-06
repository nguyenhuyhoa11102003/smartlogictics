package com.tdtu.logistics_identity_service.service.implement;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tdtu.logistics_identity_service.dto.request.AuthenticationRequest;
import com.tdtu.logistics_identity_service.dto.request.IntrospectTokenRequest;
import com.tdtu.logistics_identity_service.dto.request.LogoutRequest;
import com.tdtu.logistics_identity_service.dto.request.RefreshTokenRequest;
import com.tdtu.logistics_identity_service.dto.response.AuthenticationResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.IntrospectTokenResponseDTO;
import com.tdtu.logistics_identity_service.entity.InvalidatedToken;
import com.tdtu.logistics_identity_service.entity.Account;
import com.tdtu.logistics_identity_service.exception.ErrorCode;
import com.tdtu.logistics_identity_service.exception.AppException;
import com.tdtu.logistics_identity_service.repository.InvalidTokenRepository;
import com.tdtu.logistics_identity_service.repository.UserAccountRepository;
import com.tdtu.logistics_identity_service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {

    final UserAccountRepository userAccountRepository;

    final InvalidTokenRepository invalidatedTokenRepository;

    final PasswordEncoder passwordEncoder;

    @Value("${jwt.issuer}")
    protected String ISSUER;

    @Value("${jwt.secret-key}")
    protected String SIGNER_KEY ;

    @Value("${jwt.expired-time}")
    protected long EXPIRED_TIME;

    @Value("${jwt.refresh-signer-key}")
    protected String REFRESH_SIGNER_KEY;

    @Value("${jwt.refresh-expiration}")
    protected long REFRESH_EXPIRATION;

    @Override
    public AuthenticationResponseDTO authenticated(AuthenticationRequest request) {

        log.info("Authenticating user: {}", request.getUsername());

        Account account = userAccountRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        return AuthenticationResponseDTO.builder()
                .token(generateToken(account))
                .refreshToken(generateRefreshToken(account))
                .build();
    }

    @Override
    public IntrospectTokenResponseDTO introspectToken(IntrospectTokenRequest request) {
        String token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectTokenResponseDTO.builder().active(isValid).build();
    }

    @Override
    public void logout(LogoutRequest request) {
        try {
            SignedJWT signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiredAt(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
            log.debug("Logout account: {}", request.getToken());
        } catch (ParseException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public AuthenticationResponseDTO refreshToken(RefreshTokenRequest request) {
        log.debug("Refreshing token: {}", request.getRefreshToken());
        SignedJWT signedJWT = verifyRefreshToken(request.getRefreshToken());

        try {
            String token = generateToken(userAccountRepository
                    .findByUsername(signedJWT.getJWTClaimsSet().getSubject())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

            return AuthenticationResponseDTO.builder().token(token).build();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

    }

    private String generateRefreshToken(Account userAccount) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(ISSUER)
                .issueTime(new Date())
                .subject(userAccount.getUsername())
                .expirationTime(new Date(
                        Instant.now().plus(REFRESH_EXPIRATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(REFRESH_SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private SignedJWT verifyRefreshToken(String token) {
        try {
            log.info("Verifying refresh token: {}", token);

            JWSVerifier verifier = new MACVerifier(REFRESH_SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            boolean verified = signedJWT.verify(verifier);

            if (!verified || signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            log.debug("Refresh token is verified: {}", token);
            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer(ISSUER)
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRED_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh)  {

        try {
            log.info("Verifying token: {}", token);

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = (isRefresh)
                    ? new Date(signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESH_EXPIRATION, ChronoUnit.SECONDS)
                    .toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean verified = signedJWT.verify(verifier);

            if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

            if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            log.info("Token is verified: {}", token);

            return signedJWT;
        } catch (ParseException | JOSEException e) {
            log.error("Cannot verify token: {}", token);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(account.getRoles()))
            account.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}
