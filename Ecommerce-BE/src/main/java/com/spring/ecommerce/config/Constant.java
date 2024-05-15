package com.spring.ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Constant {

    public static String JWT_ACCESS_SECRET;
    public static String MY_EMAIL;
    @Value("${security.jwt.access-token-secret-key}")
    private String jwtAccessSecret;

    @Value("${spring.mail.username}")
    private String myEmail;

    @PostConstruct
    public void init() {
        JWT_ACCESS_SECRET = this.jwtAccessSecret;
        MY_EMAIL = this.myEmail;
    }

    public static class TIME {
        public static final int THIRTY_MINUTES = 30 * 60 * 1000;
        public static final int FOURTEEN_DAYS = 14 * 24 * 60 * 60 * 1000;
    }
}
