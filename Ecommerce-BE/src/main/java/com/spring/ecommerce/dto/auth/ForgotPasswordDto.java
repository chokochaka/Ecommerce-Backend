package com.spring.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class ForgotPasswordDto implements PasswordDto {

    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    @NotBlank(message = "Repeat password should not be blank")
    @Size(min = 8, message = "Repeat password should have at least 8 characters")
    private String repeatPassword;

    @NotNull(message = "OTP should not be null")
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
