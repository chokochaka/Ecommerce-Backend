package com.spring.ecommerce.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemDto {
    private double price;
    private String imageUrl;
    private int availableStock;
    private String variationCombination;
}
