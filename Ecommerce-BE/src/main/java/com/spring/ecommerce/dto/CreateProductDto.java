package com.spring.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@Builder
public class CreateProductDto {
    private String name;
    private String description;
    private double averageRating;
    private boolean isFeatured;
    private List<ProductItemDto> productItems = List.of();
}
