package com.tdtu.logistics_orders_service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityContextCustomer {
	public static String getCustomerId() {
		// Lấy thông tin người dùng từ SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String customerId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			// Lấy thông tin từ claims của JWT
			Jwt jwt = (Jwt) authentication.getPrincipal();
			customerId = (String) jwt.getClaims().get("customerId"); // Lấy userId từ claims
		}
		return customerId;
	}
}
