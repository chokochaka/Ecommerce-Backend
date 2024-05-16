package com.spring.ecommerce.dto.auth;

import lombok.Builder;

@Builder
public record ActiveAccountDto(String email, String verificationCode) {
}
