package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    List<ReturnOrderDto> getOrdersBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnOrderDto> getOrdersBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createOrder(CreateOrderDto createOrderDto);

    long canUserComment(CanUserComment canUserComment);
}
