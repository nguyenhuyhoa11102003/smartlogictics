package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;

public interface AccountService {
    String createAccount(CustomerRegisterAccountRequest request);

    UserInfResponseDTO getUserInfo();

    AccountInfResponseDTO updatePassword(String accountId, ChangesPasswordRequest request);

    boolean deleteAccount(String accountId);
}