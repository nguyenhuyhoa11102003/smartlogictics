package com.tdtu.common.orchestration.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface RegistryAccountWorkflow {

    @WorkflowMethod
    void processRegistryAccount(Object request);

}
