package com.spring.ecommerce.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockDto {
    private int availableStock;
    private int totalStock;
}
