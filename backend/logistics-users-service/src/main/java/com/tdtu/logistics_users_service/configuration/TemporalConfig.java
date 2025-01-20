package com.tdtu.logistics_users_service.configuration;

import com.tdtu.common.orchestration.activity.CreateReceiverActivity;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
import com.tdtu.common.orchestration.workflow.WorkerHelper;
import com.tdtu.common.orchestration.workflow.implement.CreateReceiverWorkflowImpl;
import com.tdtu.common.orchestration.workflow.implement.UserRegistrationWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TemporalConfig {

    UserRegistrationActivity userRegistrationActivity;
    CreateReceiverActivity createReceiverActivity;

    @NonFinal
    @Value("${temporal.host}")
    String target;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        log.info("Creating WorkflowServiceStubs with Temporal host: {}", target);
        WorkflowServiceStubs stubs = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .setEnableHttps(false)
                        .build()
        );
        log.info("WorkflowServiceStubs created successfully.");
        return stubs;
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs stubs) {
        log.info("Initializing WorkflowClient...");
        WorkflowClient client = WorkflowClient.newInstance(stubs);
        log.info("WorkflowClient initialized successfully.");
        return client;
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        log.info("Creating WorkerFactory...");
        WorkerFactory factory = WorkerFactory.newInstance(client);
        log.info("WorkerFactory created successfully.");
        return factory;
    }

    @Bean
    public Worker userRegistrationWorker(WorkerFactory factory) {
        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_CREATE_ACCOUNT_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(UserRegistrationWorkflowImpl.class);
        worker.registerActivitiesImplementations(userRegistrationActivity);
        return worker;
    }

    @Bean
    public Worker createReceiverWorker(WorkerFactory factory) {
        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_CREATE_ORDER_TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(CreateReceiverWorkflowImpl.class);
        worker.registerActivitiesImplementations(createReceiverActivity);

        return worker;
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(WorkerFactory workerFactory) {
        return event -> {
            log.info("Application is ready. Starting Temporal Worker Factory...");
            try {
                workerFactory.start();
                log.info("Temporal Worker Factory started successfully.");
            } catch (Exception e) {
                log.error("Failed to start Temporal Worker Factory.", e);
            }
        };
    }
}