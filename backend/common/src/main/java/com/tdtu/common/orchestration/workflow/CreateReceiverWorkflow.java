package com.tdtu.common.orchestration.workflow;

import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreateReceiverWorkflow {

    @WorkflowMethod
    String processCreateReceiver(String customerId, CreateReceiverRequest request);

}
