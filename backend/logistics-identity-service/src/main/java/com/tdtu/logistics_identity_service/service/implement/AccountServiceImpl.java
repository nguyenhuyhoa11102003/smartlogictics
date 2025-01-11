package com.tdtu.logistics_identity_service.service.implement;


import com.tdtu.common.orchestration.workflow.RegistryAccountWorkflow;
import com.tdtu.common.orchestration.workflow.WorkerHelper;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.logistics_identity_service.constant.PredefinedRole;
import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import com.tdtu.logistics_identity_service.entity.Account;
import com.tdtu.logistics_identity_service.entity.Role;
import com.tdtu.logistics_identity_service.exception.AppException;
import com.tdtu.logistics_identity_service.exception.ErrorCode;
import com.tdtu.logistics_identity_service.mapper.AccountMapper;
import com.tdtu.logistics_identity_service.repository.RoleRepository;
import com.tdtu.logistics_identity_service.repository.AccountRepository;
import com.tdtu.logistics_identity_service.service.AccountService;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    RoleRepository roleRepository;

    AccountMapper accountMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${temporal.host}")
    String target;

    @Transactional
    @Override
    public String createAccount(CustomerRegisterAccountRequest request) {

        try {
            Account account = accountMapper.toAccount(request);
            account.setPassword(passwordEncoder.encode(request.getPassword()));

            Role customerRole = roleRepository.findByName(PredefinedRole.CUSTOMER_ROLE).orElseGet(() -> roleRepository.save(Role.builder()
                    .name(PredefinedRole.CUSTOMER_ROLE)
                    .description("Customer role")
                    .build()));

            Set<Role> roles = new HashSet<>();
            roles.add(customerRole);
            account.setRoles(roles);

            account = accountRepository.save(account);
            log.info("Created account by username {}", account.getUsername());

            return account.getId();

        } catch (DataIntegrityViolationException e) {
            log.warn("Errors: Create new account-profile by cause: {}, Throw by: {}", e.getCause(), e.getClass());
            throw new AppException(ErrorCode.USER_EXISTED);
        } catch (NullPointerException e) {
            log.error("Errors: Create new account-profile by cause: {}, Throw by: {}", e.getCause(), e.getClass());
            throw new AppException(ErrorCode.PROFILE_NOT_EXISTED);
        }
    }

    private CustomerInfResponse createCustomer(CustomerRegisterAccountRequest request) {

        try {

            var workerClient = WorkerHelper.getWorkflowClient(target);

            WorkflowOptions options = WorkflowOptions.newBuilder()
                    .setTaskQueue(WorkerHelper.WORKFLOW_CREATE_ACCOUNT_TASK_QUEUE)
                    .build();

            RegistryAccountWorkflow workflow = workerClient.newWorkflowStub(RegistryAccountWorkflow.class, options);

            return workflow.processRegistryAccount(request);
        } catch (WorkflowException exception) {
            log.error("Workflow failed for request: {}", request, exception);
            throw new AppException(ErrorCode.WORKFLOW_FAILED);
        }

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

    @Transactional
    @Override
    public AccountInfResponseDTO updatePassword(String accountId, ChangesPasswordRequest request) {
        return null;
    }

    @Override
    public boolean deleteAccount(String accountId) {
        log.debug("Delete account by id {}", accountId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        accountRepository.delete(account);

        log.info("Deleted account by id {}", accountId);

        return true;
    }
}
