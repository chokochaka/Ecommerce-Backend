package com.spring.ecommerce.dto.order;

import com.spring.ecommerce.dto.user.ReturnUserDto;
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
    private OrderDto order;
    private List<OrderDetailDto> orderDetails;
}

