package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.dto.request.AuthenticationRequest;
import com.tdtu.logistics_identity_service.dto.request.IntrospectTokenRequest;
import com.tdtu.logistics_identity_service.dto.request.LogoutRequest;
import com.tdtu.logistics_identity_service.dto.request.RefreshTokenRequest;
import com.tdtu.logistics_identity_service.dto.response.AuthenticationResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.IntrospectTokenResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO authenticated(AuthenticationRequest request);

    IntrospectTokenResponseDTO introspectToken(IntrospectTokenRequest request);

    void logout(LogoutRequest request);

    AuthenticationResponseDTO refreshToken(RefreshTokenRequest request);
}
