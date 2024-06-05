package com.spring.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CanUserComment {

    @NotNull(message = "User ID should not be null")
    private Long userId;

    @NotNull(message = "Product ID should not be null")
    private Long productId;
}
