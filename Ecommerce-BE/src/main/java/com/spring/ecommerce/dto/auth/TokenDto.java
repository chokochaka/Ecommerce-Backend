package com.spring.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @NotBlank(message = "Access token should not be blank")
    private String accessToken;

    @NotBlank(message = "Refresh token should not be blank")
    private String refreshToken;
}
