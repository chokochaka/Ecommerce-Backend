package com.spring.ecommerce.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RatingDto {

    private Long id;

    @NotNull(message = "Product ID should not be null")
    private long productId;

    @NotNull(message = "User ID should not be null")
    private long userId;

    @NotBlank(message = "User name should not be blank")
    private String userName;

    @NotNull(message = "Order detail ID should not be null")
    private long orderDetailId;

    @Min(value = 1, message = "Rating value should not be less than 1")
    @Max(value = 5, message = "Rating value should not be more than 5")
    private int ratingValue;

    @NotBlank(message = "Review value should not be blank")
    private String reviewValue;
}
