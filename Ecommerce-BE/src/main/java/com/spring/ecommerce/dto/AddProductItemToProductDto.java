package com.spring.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductItemToProductDto {
    private long productId; // This is the id of the product to which this item belongs
    private int availableStock;
    private double price;
    private String imageUrl;
    private String variationCombination;
}
