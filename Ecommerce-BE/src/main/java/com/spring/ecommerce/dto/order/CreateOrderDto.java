package com.spring.ecommerce.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class CreateOrderDto {

    @NotNull(message = "Order should not be null")
    private OrderDto order;

    @NotEmpty(message = "Order details should not be empty")
    private List<OrderDetailDto> orderDetails;
}
