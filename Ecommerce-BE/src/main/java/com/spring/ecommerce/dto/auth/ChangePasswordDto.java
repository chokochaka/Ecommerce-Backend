package com.spring.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class ChangePasswordDto implements PasswordDto {

    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    @NotBlank(message = "Repeat password should not be blank")
    @Size(min = 8, message = "Repeat password should have at least 8 characters")
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
