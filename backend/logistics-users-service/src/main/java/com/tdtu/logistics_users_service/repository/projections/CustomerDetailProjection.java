package com.tdtu.logistics_users_service.repository.projections;

import com.tdtu.logistics_users_service.entity.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customerSummary", types = Customer.class)
public interface CustomerDetailProjection {
    String getId();
    String getFullName();
    String getPhoneNumber();
    String getEmail();
}
