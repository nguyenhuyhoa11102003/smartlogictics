package com.tdtu.logistics_users_service.configuration;

import com.tdtu.logistics_users_service.entity.Address;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.entity.Receiver;
import com.tdtu.logistics_users_service.entity.Sender;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Sender.class)
                .exposeIdsFor(Address.class)
                .exposeIdsFor(Customer.class)
                .exposeIdsFor(Receiver.class)
        ;

    }
}