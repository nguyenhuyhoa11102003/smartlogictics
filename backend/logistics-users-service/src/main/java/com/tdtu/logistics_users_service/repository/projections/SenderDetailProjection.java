package com.tdtu.logistics_users_service.repository.projections;

import com.tdtu.logistics_users_service.entity.Sender;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "senderSummary", types = Sender.class)
public interface SenderDetailProjection {
    String getId();
    String getFullName();
    String getPhoneNumber();
    String getEmail();
    String getProvince();
    String getDistrict();
    String getWard();
    String getStreet();
}