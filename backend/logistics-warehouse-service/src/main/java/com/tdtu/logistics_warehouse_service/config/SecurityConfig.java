package com.tdtu.logistics_warehouse_service.config;//package com.tdtu.logistics_goods_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
public class SecurityConfig extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	private static final String[] WAREHOUSE_WHITELIST = {
			"/swagger-ui/**",
			"/swagger-ui-custom.html",
			"/actuator/prometheus",
			"/actuator/health/**",
			"/v3/api-docs/**"
	};


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(request -> request.requestMatchers(WAREHOUSE_WHITELIST)
						.permitAll()
						.anyRequest()
						.authenticated()
				);
		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		return httpSecurity.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
		Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
			Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
			Collection<String> roles = realmAccess.get("roles");
			return roles.stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
					.collect(Collectors.toList());
		};
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		String method = request.getMethod();
		String clientIp = request.getRemoteAddr();

		filterChain.doFilter(request, response);

		int status = response.getStatus();
		logger.info("Access Identity Service - IP: {}, Method: {}, URI: {}, Status: {}", clientIp, method, requestUri, status);
	}

}
