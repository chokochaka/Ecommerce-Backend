package com.spring.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserRefreshToken {

    private Long id;

    @NotBlank(message = "Refresh token should not be blank")
    private String refreshToken;

    @NotNull(message = "Issued at should not be null")
    private Instant issuedAt;

    @NotNull(message = "Expires at should not be null")
    private Instant expiresAt;

    @NotNull(message = "User ID should not be null")
    private Long user; // User id
}
