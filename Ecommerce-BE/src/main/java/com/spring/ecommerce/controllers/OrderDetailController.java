package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderDetail")
@RequiredArgsConstructor
@Tag(name = "Order Detail", description = "Order Detail API")
@Slf4j
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("/search")
    public ResponseEntity<List<ReturnOrderDetailDto>> getOrdersBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        List<ReturnOrderDetailDto> orders = orderDetailService.getOrderDetailsBySearch(searchRequestDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnOrderDetailDto>> getOrdersBySearchAndPagination(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<ReturnOrderDetailDto> orders = orderDetailService.getOrderDetailsBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable long id) {
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReturnOrderDetailDto>> getOrdersByUserId(@PathVariable long userId) {
        List<ReturnOrderDetailDto> orders = orderDetailService.getOrderDetailsByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
