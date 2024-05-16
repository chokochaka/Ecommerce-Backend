package com.spring.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetailsDto {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
