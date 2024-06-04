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
public class ReturnOrderDetailDto {

    private Long userId;
    private String imageUrl;

    private Long productId;
    private String productName;
    private String description;
    private int quantity;
    private double price;

    private long orderDetailOrderId;

    private Instant createdOn;
    private Instant lastUpdatedOn;
}
