package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderDetailService {
    List<ReturnOrderDetailDto> getOrderDetailsBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnOrderDetailDto> getOrderDetailsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void deleteOrderDetail(long id);

    List<ReturnOrderDetailDto> getOrderDetailsByUserId(long userId);
}
