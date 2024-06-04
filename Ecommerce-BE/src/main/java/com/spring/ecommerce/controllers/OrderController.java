package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/search")
    public List<ReturnOrderDto> getOrdersBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return orderService.getOrdersBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ReturnOrderDto> getOrdersBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return orderService.getOrdersBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createOrder(@RequestBody CreateOrderDto createOrderDto) {
        orderService.createOrder(createOrderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/approve/{orderId}")
    public void approveOrder(@PathVariable long orderId) {
        orderService.approveOrder(orderId);
    }

    @PostMapping("/canUserComment")
    public long canUserComment(@RequestBody CanUserComment canUserComment) {
        return orderService.canUserComment(canUserComment);
    }
}
