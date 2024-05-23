package com.spring.ecommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.ecommerce.dto.category.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ReturnProductDto {
    private Long id;

    private String name;
    private String description;
    private double averageRating;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    private Set<CategoryDto> categories;

    private Instant createdOn;
    private Instant lastUpdatedOn;
}
