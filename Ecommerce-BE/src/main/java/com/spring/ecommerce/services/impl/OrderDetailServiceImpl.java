package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.OrderDetailMapper;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import com.spring.ecommerce.repositories.OrderDetailRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final FilterSpecificationService<OrderDetail> orderDetailFilterSpecificationService;


    @Override
    public List<ReturnOrderDetailDto> getOrderDetailsBySearch(SearchRequestDto searchRequestDto) {
        Specification<OrderDetail> orderDetailSearchSpecification = orderDetailFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return orderDetailRepository.findAll(orderDetailSearchSpecification).stream()
                .map(orderDetailMapper::orderDetailToReturnOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReturnOrderDetailDto> getOrderDetailsBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<OrderDetail> orderDetailSearchSpecification = orderDetailFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        Page<OrderDetail> orderDetailPage = orderDetailRepository.findAll(orderDetailSearchSpecification, pageable);
        return orderDetailPage.map(orderDetailMapper::orderDetailToReturnOrderDto);
    }

    @Override
    public void deleteOrderDetail(long id) {
        orderDetailRepository.deleteById(id);
    }
}
