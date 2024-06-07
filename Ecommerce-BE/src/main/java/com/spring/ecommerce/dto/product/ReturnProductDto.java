package com.spring.ecommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.rating.ReturnRatingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class ReturnProductDto {
    private Long id;

    private String name;
    private String description;
    private double averageRating;
    private String imageUrl;
    private double price;

    @JsonProperty("featured")
    private boolean isFeatured;

    private Set<CategoryDto> categories;

    private List<ReturnRatingDto> ratings;

    private Instant createdOn;
    private Instant lastUpdatedOn;
}
