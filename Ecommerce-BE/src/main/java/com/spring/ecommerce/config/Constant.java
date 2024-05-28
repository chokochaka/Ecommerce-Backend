package com.spring.ecommerce.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {
    public static class TIME {
        public static final int THIRTY_MINUTES = 30 * 60 * 1000; // access token, mail
        public static final int TWO_HOURS = 2 * 60 * 60 * 1000;
        public static final int FOURTEEN_DAYS = 14 * 24 * 60 * 60 * 1000; // refresh token
    }
}
