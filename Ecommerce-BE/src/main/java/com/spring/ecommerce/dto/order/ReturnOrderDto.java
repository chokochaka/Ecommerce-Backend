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
public class ReturnOrderDto {
    private Long id;

    private int totalQuantity;
    private double totalPrice;

    private boolean approved;
    private String commodityCode;

    private long orderUserId;
    private long orderProductId;

    private Instant issuedAt;
}
