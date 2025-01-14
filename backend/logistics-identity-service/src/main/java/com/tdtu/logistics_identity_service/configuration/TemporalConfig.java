package com.tdtu.logistics_identity_service.configuration;

import com.tdtu.common.orchestration.activity.RegistryAccountActivity;
import com.tdtu.common.orchestration.workflow.RegistryAccountWorkflow;
import com.tdtu.common.orchestration.workflow.WorkerHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
//@RequiredArgsConstructor
//public class TemporalConfig {
//
//    RegistryAccountActivity registryAccountActivity;
//
//    @NonFinal
//    @Value("${temporal.serviceAddress}")
//    String target;
//
//    @PostConstruct
//    public void startWorker() {
//
//        var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
//                .setTarget(target)
//                .setEnableHttps(true)
//                .build());
//
//        var client = WorkflowClient.newInstance(stub);
//
//        var factory = WorkerFactory.newInstance(client);
//
//        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_CREATE_ACCOUNT_TASK_QUEUE);
//
//        worker.registerWorkflowImplementationTypes(RegistryAccountWorkflow.class);
//        worker.registerActivitiesImplementations(registryAccountActivity);
//
//        factory.start();
//    }
//
//}
