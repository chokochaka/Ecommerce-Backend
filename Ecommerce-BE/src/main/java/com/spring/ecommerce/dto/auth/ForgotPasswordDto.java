package com.spring.ecommerce.dto.auth;

import lombok.Builder;

@Builder
public class ForgotPasswordDto implements PasswordDto {
    private String password;
    private String repeatPassword;
    private Integer otp;

    // Constructors, getters, and setters

    @Override
    public String password() {
        return password;
    }

    @Override
    public String repeatPassword() {
        return repeatPassword;
    }

    public Integer otp() {
        return otp;
    }
}