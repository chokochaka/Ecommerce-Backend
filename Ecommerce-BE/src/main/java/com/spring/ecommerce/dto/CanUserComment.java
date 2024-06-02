package com.spring.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CanUserComment {
    private Long userId;
    private Long productId;
}
