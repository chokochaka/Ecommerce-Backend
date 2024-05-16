package com.spring.ecommerce.dto.auth;

import lombok.Builder;

@Builder
public record ChangePasswordDto(String password, String repeatPassword) {
}
