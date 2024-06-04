package com.spring.ecommerce.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
public class OrderDetailDto {
    private Long userId;
    private Long productId;
    private String imageUrl;
    private String productName;
    private String description;
    private int quantity;
    private double price;

}
