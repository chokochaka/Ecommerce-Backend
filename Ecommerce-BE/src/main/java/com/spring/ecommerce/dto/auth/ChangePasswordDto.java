package com.spring.ecommerce.dto.auth;

import lombok.Builder;

@Builder
public class ChangePasswordDto implements PasswordDto {
    private String password;
    private String repeatPassword;
    
    @Override
    public String password() {
        return password;
    }

    @Override
    public String repeatPassword() {
        return repeatPassword;
    }
}
