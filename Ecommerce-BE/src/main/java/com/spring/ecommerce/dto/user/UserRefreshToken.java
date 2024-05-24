package com.spring.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserRefreshToken {
    private Long id;
    private String refreshToken;
    private Instant issuedAt;
    private Instant expiresAt;
    private Long user; // User id
}
