package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderDetailService {
    List<ReturnOrderDetailDto> getOrderDetailsBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnOrderDetailDto> getOrderDetailsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void deleteOrderDetail(long id);
}
