package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;

public interface OrderService {
    void createOrder(CreateOrderDto createOrderDto);

    long canUserComment(CanUserComment canUserComment);
}
