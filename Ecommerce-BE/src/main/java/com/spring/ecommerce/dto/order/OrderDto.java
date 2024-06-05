package com.spring.ecommerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class OrderDto {

    @NotNull(message = "User ID should not be null")
    private Long userId;

    @Min(value = 1, message = "Total quantity should be at least 1")
    private int totalQuantity;

    @Min(value = 0, message = "Total price should not be negative")
    private double totalPrice;
}
