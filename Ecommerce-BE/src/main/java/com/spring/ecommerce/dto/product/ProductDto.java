package com.spring.ecommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ProductDto {

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Description should not be blank")
    private String description;

    @Min(value = 0, message = "Average rating should not be negative")
    @Max(value = 5, message = "Average rating should not be greater than 5")
    private double averageRating;

    @NotBlank(message = "Image URL should not be blank")
    private String imageUrl;

    @Min(value = 0, message = "Price should not be negative")
    private double price;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    @NotNull(message = "Category IDs should not be null")
    private Set<Long> categoryIds;
}
