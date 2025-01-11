package com.tdtu.logistics_identity_service.configuration;

import com.tdtu.common.orchestration.activity.RegistryAccountActivity;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TemporalConfig {

    RegistryAccountActivity registryAccountActivity;

    @NonFinal
    @Value("${temporal.host}")
    String target;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .setEnableHttps(true)
                        .build()
        );
    }


    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs stubs) {
        return WorkflowClient.newInstance(stubs);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        return WorkerFactory.newInstance(client);
    }

    @Bean
    public Worker registryAccountWorker(WorkerFactory factory) {
        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_CREATE_ACCOUNT_TASK_QUEUE);
        worker.registerActivitiesImplementations(registryAccountActivity);
        return worker;
    }
//
//    @PostConstruct
//    public void startFactory(WorkerFactory factory) {
//        factory.start();
//    }
}
