package com.spring.ecommerce.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Builder
@ToString
@Getter
@Setter
public class OrderDto {
    private Long userId;
    private int totalQuantity;
    private double totalPrice;
}
