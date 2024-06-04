package com.spring.ecommerce.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ReturnProductItemDto {
    private Long id;
    private double price;
    private String imageUrl;
    private int availableStock;
    private String variationCombination;
    private Long productId;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    private StockDto stock;
    private ReturnProductDto product;

}

