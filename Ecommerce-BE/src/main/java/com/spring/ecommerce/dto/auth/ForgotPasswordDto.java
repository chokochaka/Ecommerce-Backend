package com.spring.ecommerce.dto.auth;

import lombok.Builder;

@Builder
public record ForgotPasswordDto(String password, String repeatPassword, Integer otp) {
}
