package com.spring.ecommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private double averageRating;
    private String imageUrl;
    private double price;
    
    @JsonProperty("isFeatured")
    private boolean isFeatured;

    Set<Long> categoryIds;
}
