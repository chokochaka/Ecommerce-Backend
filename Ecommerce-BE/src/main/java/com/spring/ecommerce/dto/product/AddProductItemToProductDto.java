package com.spring.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProductItemToProductDto {

    @NotNull(message = "Product ID should not be null")
    private long productId; // This is the id of the product to which this item belongs

    @Min(value = 0, message = "Available stock should not be negative")
    private int availableStock;

    @Min(value = 0, message = "Price should not be negative")
    private double price;

    @NotBlank(message = "Image URL should not be blank")
    private String imageUrl;

    @NotBlank(message = "Variation combination should not be blank")
    private String variationCombination;
}
