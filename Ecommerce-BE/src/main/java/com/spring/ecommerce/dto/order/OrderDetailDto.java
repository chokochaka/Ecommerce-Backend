package com.spring.ecommerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderDetailDto {

    @NotNull(message = "User ID should not be null")
    private Long userId;

    @NotNull(message = "Product ID should not be null")
    private Long productId;

    @NotBlank(message = "Image URL should not be blank")
    private String imageUrl;

    @NotBlank(message = "Product name should not be blank")
    private String productName;

    @NotBlank(message = "Description should not be blank")
    private String description;

    @Min(value = 1, message = "Quantity should be at least 1")
    private int quantity;

    @Min(value = 0, message = "Price should not be negative")
    private double price;
}
