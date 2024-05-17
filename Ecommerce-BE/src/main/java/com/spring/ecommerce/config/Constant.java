package com.spring.ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {

//    @PostConstruct
//    public void init() {}

    public static class TIME {
        public static final int THIRTY_MINUTES = 30 * 60 * 1000; // access token, mail
        public static final int TWO_HOURS = 2 * 60 * 60 * 1000;
        public static final int FOURTEEN_DAYS = 14 * 24 * 60 * 60 * 1000; // refresh token
    }
}
