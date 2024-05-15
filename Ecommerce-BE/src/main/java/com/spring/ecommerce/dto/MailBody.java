package com.spring.ecommerce.dto;

import lombok.Builder;

@Builder
public record MailBody(String recipient, String subject, String text) {
}
