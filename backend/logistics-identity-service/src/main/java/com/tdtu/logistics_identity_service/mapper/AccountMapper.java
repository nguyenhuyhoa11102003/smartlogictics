package com.tdtu.logistics_identity_service.mapper;

import com.tdtu.logistics_identity_service.dto.request.CreateAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toUserAccount(CreateAccountRequest createAccountRequest);

    CreateAccountResponseDTO toCreateAccountResponse(Account userAccount);
}
