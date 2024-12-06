package com.tdtu.logistics_identity_service.service.implement;


import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.logistics_identity_service.dto.request.CreateAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import com.tdtu.logistics_identity_service.mapper.AccountMapper;
import com.tdtu.logistics_identity_service.repository.RoleRepository;
import com.tdtu.logistics_identity_service.repository.UserAccountRepository;
import com.tdtu.logistics_identity_service.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountRepository userAccountRepository;

    RoleRepository roleRepository;

    AccountMapper userAccountMapper;

    PasswordEncoder passwordEncoder;


    @Override
    public CreateAccountResponseDTO createAccount(CreateAccountRequest request) {
        return null;
    }

    @Override
    public UserInfResponseDTO getUserInfo() {
        return null;
    }

    @Override
    public List<AccountInfResponseDTO> getAllAccounts() {
        return List.of();
    }

    @Override
    public Page<AccountInfResponseDTO> getAccounts(Pageable pageable) {
        return null;
    }

    @Override
    public AccountInfResponseDTO updatePassword(String accountId, ChangesPasswordRequest request) {
        return null;
    }
}
