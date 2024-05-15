package com.spring.ecommerce.dto;

import lombok.Builder;

@Builder
public record ChangePassword(String password, String repeatPassword) {
}
