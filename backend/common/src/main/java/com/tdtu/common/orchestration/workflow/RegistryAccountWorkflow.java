package com.tdtu.common.orchestration.workflow;

import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface RegistryAccountWorkflow {

    @WorkflowMethod
    CustomerInfResponse processRegistryAccount(Object request);

}
