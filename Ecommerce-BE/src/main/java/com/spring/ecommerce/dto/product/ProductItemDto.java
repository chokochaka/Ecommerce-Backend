package com.spring.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductItemDto {

    @Min(value = 0, message = "Price should not be negative")
    private double price;

    @NotBlank(message = "Image URL should not be blank")
    private String imageUrl;

    @Min(value = 0, message = "Available stock should not be negative")
    private int availableStock;

    @NotBlank(message = "Variation combination should not be blank")
    private String variationCombination;
}
