package com.spring.ecommerce.dto;

import lombok.Builder;

@Builder
public record MailBodyDto(String recipient, String subject, String text) {
}
