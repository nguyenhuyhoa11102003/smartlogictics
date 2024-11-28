package com.tdtu.logistics_identity_service.service.implement;


import com.nimbusds.jose.proc.SecurityContext;
import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.logistics_identity_service.dto.request.CreateAccountRequest;
import com.tdtu.logistics_identity_service.dto.request.CreateProfileRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import com.tdtu.logistics_identity_service.entity.Role;
import com.tdtu.logistics_identity_service.entity.UserAccount;
import com.tdtu.logistics_identity_service.enumrator.ErrorCode;
import com.tdtu.logistics_identity_service.exception.AppException;
import com.tdtu.logistics_identity_service.mapper.UserAccountMapper;
import com.tdtu.logistics_identity_service.repository.RoleRepository;
import com.tdtu.logistics_identity_service.repository.UserAccountRepository;
import com.tdtu.logistics_identity_service.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountRepository userAccountRepository;

    RoleRepository roleRepository;

    UserAccountMapper userAccountMapper;

    PasswordEncoder passwordEncoder;

    ProfileClientService profileClientService;


    @Override
    @Transactional
    public CreateAccountResponseDTO createAccount(CreateAccountRequest request) {
        try {
            CreateAccountResponseDTO profileResponse =
                    profileClientService.createUserProfile(CreateProfileRequest.builder()
                                    .fullName(request.getFullName())
                                    .email(request.getEmail())
                                    .phoneNumber(request.getPhoneNumber())
                                    .gender(request.getGender())
                                    .dob(request.getDob()).build())
                            .block();

            log.debug("Created profile.....");

            UserAccount userAccount = userAccountMapper.toUserAccount(request);
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            userAccount.setProfileId(Objects.requireNonNull(profileResponse).getId());

            Role customerRole = roleRepository.findByName(PredefinedRole.CUSTOMER_ROLE);
            Set<Role> roles = new HashSet<>();
            roles.add(customerRole);
            userAccount.setRoles(roles);

            userAccount =   userAccountRepository.save(userAccount);
            log.info("Created account by username {}", userAccount.getUsername());

            return toCreateAccountResp(profileResponse, userAccount);
        } catch (DataIntegrityViolationException e) {
            log.warn("Errors: Create new account-profile by cause: {}, Throw by: {}", e.getCause(), e.getClass());
            throw new AppException(ErrorCode.USER_EXISTED);
        } catch (NullPointerException e) {
            log.error("Errors: Create new account-profile by cause: {}, Throw by: {}", e.getCause(), e.getClass());
            throw new AppException(ErrorCode.PROFILE_NOT_EXISTED);
        }
    }

    @Override
    public UserInfResponseDTO getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String author = context.getAuthentication().getName();
        log.debug("Get user info by username: {}", author);

        UserAccount userAccount = userAccountRepository.findByUsername(author)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));

        try {
            UserInfResponseDTO userInfResponseDTO =
                    profileClientService.getUserProfile(userAccount.getProfileId())
                            .block();

            if (Objects.nonNull(userInfResponseDTO)) {
                userInfResponseDTO.setAccountId(userAccount.getId());
                userInfResponseDTO.setUsername(userAccount.getUsername());
            }

            return userInfResponseDTO;
        } catch (DataIntegrityViolationException e) {
            log.error("Errors: Get user's info have problem: by cause: {}, Throw by: {}", e.getCause(), e.getClass());
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    @Override
    public List<AccountInfResponseDTO> getAllAccounts() {
        return userAccountRepository.findAll().stream()
                .map(this::toAccountInfResponse).toList();
    }

    @Override
    public Page<AccountInfResponseDTO> getAccounts(Pageable pageable) {
        return userAccountRepository.findAll(pageable)
                .map(this::toAccountInfResponse);
    }

    @Override
    @Transactional
    public AccountInfResponseDTO updatePassword(String accountId, ChangesPasswordRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        String author = context.getAuthentication().getName();

        UserAccount userAccount = userAccountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!request.getUsername().equals(author) || !passwordEncoder.matches(request.getCurrentPassword(), userAccount.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userAccount = userAccountRepository.save(userAccount);
        log.debug("Updated password by username {}", userAccount.getUsername());

        return toAccountInfResponse(userAccount);
    }

    private CreateAccountResponseDTO toCreateAccountResp(CreateAccountResponseDTO responseDTO, UserAccount account) {
        responseDTO.setAccountId(account.getId());
        responseDTO.setUsername(account.getUsername());
        responseDTO.setCreatedDate(account.getCreatedDate());
        responseDTO.setLastModifiedDate(account.getLastModifiedDate());
        responseDTO.setLastModifiedBy(account.getLastModifiedBy());
        return responseDTO;
    }

    private AccountInfResponseDTO toAccountInfResponse(UserAccount userAccount) {
        return AccountInfResponseDTO.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .profileId(userAccount.getProfileId())
                .createdDate(userAccount.getCreatedDate())
                .lastModifiedDate(userAccount.getLastModifiedDate())
                .roles(userAccount.getRoles().stream().map(Role::getName).collect(toSet()))
                .build();
    }
}
