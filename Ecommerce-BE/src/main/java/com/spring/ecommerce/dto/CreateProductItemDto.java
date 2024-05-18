package com.spring.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductItemDto {
    private String availableStock;
    private double price;
    private String imageUrl;
    private String variationCombination;
}
