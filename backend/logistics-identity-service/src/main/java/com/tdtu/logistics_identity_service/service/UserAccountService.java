package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.logistics_identity_service.dto.request.CreateAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserAccountService {
    CreateAccountResponseDTO createAccount(CreateAccountRequest request);

    UserInfResponseDTO getUserInfo();

    List<AccountInfResponseDTO> getAllAccounts();

    Page<AccountInfResponseDTO> getAccounts(Pageable pageable);

    AccountInfResponseDTO updatePassword(String accountId, ChangesPasswordRequest request);
}