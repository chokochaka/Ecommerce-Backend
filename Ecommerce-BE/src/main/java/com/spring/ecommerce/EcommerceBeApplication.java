package com.spring.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(EcommerceBeApplication.class, args);
    }


}
