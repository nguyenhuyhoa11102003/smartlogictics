package com.tdtu.logistics_warehouse_service.config;

import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtDecoderConfig implements JwtDecoder {

	@Override
	public Jwt decode(String token) throws JwtException {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			return new Jwt(
					token,
					signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
					signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
					signedJWT.getHeader().toJSONObject(),
					signedJWT.getJWTClaimsSet().getClaims()
			);
		} catch (ParseException e) {
			throw new JwtException("Invalid token", e);
		}
	}
}
