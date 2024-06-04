package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderDetailService;
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
@RequestMapping("/api/v1/orderDetail")
@RequiredArgsConstructor
@Tag(name = "Order Detail", description = "Order Detail API")
@Slf4j
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("/search")
    public List<ReturnOrderDetailDto> getOrdersBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return orderDetailService.getOrderDetailsBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ReturnOrderDetailDto> getOrdersBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return orderDetailService.getOrderDetailsBySearchAndPagination(searchRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable long id) {
        orderDetailService.deleteOrderDetail(id);
    }

}
