package com.spring.ecommerce.dto;

import lombok.Builder;

@Builder
public record ChangePasswordDto(String password, String repeatPassword) {
}
