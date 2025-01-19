package com.tdtu.common.orchestration.workflow;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UserRegistrationWorkflow {

    @WorkflowMethod
    CustomerInfResponse processRegistryAccount(CustomerRegisterAccountRequest request);


}
