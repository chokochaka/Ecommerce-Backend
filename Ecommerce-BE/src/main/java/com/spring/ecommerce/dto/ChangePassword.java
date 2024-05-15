package com.spring.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record ChangePassword(String password, String repeatPassword) {
}
