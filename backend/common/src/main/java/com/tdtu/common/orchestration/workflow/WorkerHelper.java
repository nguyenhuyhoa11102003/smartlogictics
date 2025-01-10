package com.tdtu.common.orchestration.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import java.time.Duration;
public class WorkerHelper {

    public static final String WORKFLOW_CREATE_ACCOUNT_TASK_QUEUE = "CreateAccountTaskQueue";

    private static final RetryOptions RETRY_OPTIONS = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();

    // Private constructor to prevent instantiation
    private WorkerHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static WorkflowOptions getWorkflowOptions(String taskQueue, String workflowId) {
        var builder = WorkflowOptions.newBuilder();

        builder.setWorkflowId(workflowId);
        builder.setTaskQueue(taskQueue);
        builder.setWorkflowRunTimeout(java.time.Duration.ofMinutes(5));
        builder.setWorkflowTaskTimeout(java.time.Duration.ofMinutes(1));
        return builder.build();
    }

    public static WorkflowClient getWorkflowClient(String target) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setTarget(target)
                .setEnableHttps(false)
                .build());
        return WorkflowClient.newInstance(service);
    }

    public static ActivityOptions defaultActivityOptions() {
        return ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(5))
                .setRetryOptions(RETRY_OPTIONS)
                .build();
    }
}
