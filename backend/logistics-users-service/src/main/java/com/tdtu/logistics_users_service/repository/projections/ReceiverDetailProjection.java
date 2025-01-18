package com.tdtu.logistics_users_service.repository.projections;

import com.tdtu.logistics_users_service.entity.Receiver;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "receiverDetail", types = Receiver.class)
public interface ReceiverDetailProjection {
    String getId();
    String getFullName();
    String getPhoneNumber();
    String getEmail();
    String getProvince();
    String getDistrict();
    String getWard();
    String getStreet();
}