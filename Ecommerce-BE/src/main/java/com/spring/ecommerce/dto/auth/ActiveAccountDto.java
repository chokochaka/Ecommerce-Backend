package com.spring.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ActiveAccountDto(
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Verification code should not be blank")
        String verificationCode
) {
}
