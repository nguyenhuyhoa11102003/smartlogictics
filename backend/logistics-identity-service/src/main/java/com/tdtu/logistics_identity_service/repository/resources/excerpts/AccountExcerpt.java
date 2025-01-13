package com.tdtu.logistics_identity_service.repository.resources.excerpts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tdtu.logistics_identity_service.entity.Account;
import com.tdtu.logistics_identity_service.entity.Role;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;
import java.util.stream.Collectors;

@Projection(name = "accountExcerpt", types = {Account.class})
public interface AccountExcerpt {
    String getId();

    String getUsername();

    String getUserProfileId();

    default String getRoles() {
        Set<Role> roles = getRolesSet();
        return (roles != null ? roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")) : "");
    }

    @JsonIgnore
    Set<Role> getRolesSet();
}
