package com.spring.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private double averageRating;
    @JsonProperty("isFeatured")
    private boolean isFeatured;
}
