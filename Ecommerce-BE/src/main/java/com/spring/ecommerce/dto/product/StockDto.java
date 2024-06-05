package com.spring.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockDto {

    @Min(value = 0, message = "Available stock should not be negative")
    private int availableStock;

    @Min(value = 0, message = "Total stock should not be negative")
    private int totalStock;
}
