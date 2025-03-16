package com.tabcorp.saleReport.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Order(1)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges ->
                        exchanges.anyExchange().authenticated())
                .oauth2Login(oAuth2LoginSpec -> {})                 // Enable OAuth2 login
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // Enable JWT for resource server
        return http.build();
    }
}