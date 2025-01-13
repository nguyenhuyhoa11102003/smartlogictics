package com.tdtu.logistics_identity_service.repository.resources.projection;

import com.tdtu.logistics_identity_service.entity.Account;
import org.springframework.data.rest.core.config.Projection;


@Projection(name = "accountProjection", types = {Account.class})
public interface AccountProjection {

    String getId();

    String getUsername();

    String getUserProfileId();
}
