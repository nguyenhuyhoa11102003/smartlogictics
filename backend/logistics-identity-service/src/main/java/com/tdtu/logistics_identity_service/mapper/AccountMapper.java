package com.tdtu.logistics_identity_service.mapper;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(CustomerRegisterAccountRequest createAccountRequest);

    CreateAccountResponseDTO toCreateAccountResponse(Account userAccount);
}
