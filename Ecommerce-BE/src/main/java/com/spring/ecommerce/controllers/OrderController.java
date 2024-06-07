package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReturnOrderDto>> getOrdersBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        List<ReturnOrderDto> orders = orderService.getOrdersBySearch(searchRequestDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnOrderDto>> getOrdersBySearchAndPagination(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<ReturnOrderDto> orders = orderService.getOrdersBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        orderService.createOrder(createOrderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/approve/{orderId}")
    public ResponseEntity<Void> approveOrder(@PathVariable long orderId) throws MessagingException {
        orderService.approveOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/canUserComment")
    public ResponseEntity<Long> canUserComment(@Valid @RequestBody CanUserComment canUserComment) {
        long result = orderService.canUserComment(canUserComment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
