package com.spring.ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {

    public static String JWT_ACCESS_SECRET;
    public static String JWT_REFRESH_SECRET;

    @Value("${security.jwt.access-token-secret-key}")
    private String jwtAccessSecret;
    @Value("${security.jwt.refresh-token-secret-key}")
    private String jwtRefreshSecret;

    @PostConstruct
    public void init() {
        JWT_ACCESS_SECRET = this.jwtAccessSecret;
        JWT_REFRESH_SECRET = this.jwtRefreshSecret;
    }

    public static class ROLE {
        public static final String ROLE_USER = "USER";
        public static final String ROLE_INVENTORY_MANAGER = "INVENTORY_MANAGER";
        public static final String ROLE_ADMIN = "ADMIN";
    }
}
