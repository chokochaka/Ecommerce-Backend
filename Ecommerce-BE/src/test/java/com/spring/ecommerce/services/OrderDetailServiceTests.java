package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.OrderDetailMapper;
import com.spring.ecommerce.models.OrderDetail;
import com.spring.ecommerce.repositories.OrderDetailRepository;
import com.spring.ecommerce.services.impl.OrderDetailServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTests {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    @Mock
    private FilterSpecificationService<OrderDetail> orderDetailFilterSpecificationService;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    private OrderDetail orderDetail;
    private ReturnOrderDetailDto returnOrderDetailDto;
    private SearchRequestDto searchRequestDto;

    @BeforeEach
    public void setup() {
        orderDetail = new OrderDetail();
        orderDetail.setId(1L);

        returnOrderDetailDto = ReturnOrderDetailDto.builder()
                .id(1L)
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());
    }

    @AfterEach
    public void tearDown() {
        reset(orderDetailRepository, orderDetailMapper, orderDetailFilterSpecificationService);
    }

    @DisplayName("JUnit test for getOrderDetailsBySearch method")
    @Test
    public void getOrderDetailsBySearch_ShouldReturnOrderDetails() {
        // Given
        Specification<OrderDetail> specification = mock(Specification.class);
        when(orderDetailFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(orderDetailRepository.findAll(specification)).thenReturn(Collections.singletonList(orderDetail));
        when(orderDetailMapper.orderDetailToReturnOrderDto(orderDetail)).thenReturn(returnOrderDetailDto);

        // When
        List<ReturnOrderDetailDto> result = orderDetailService.getOrderDetailsBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnOrderDetailDto.getId());
    }

    @DisplayName("JUnit test for deleteOrderDetail method")
    @Test
    public void deleteOrderDetail_ShouldDeleteOrderDetail() {
        // Given
        long id = 1L;

        // When
        orderDetailService.deleteOrderDetail(id);

        // Then
        verify(orderDetailRepository, times(1)).deleteById(id);
    }

    @DisplayName("JUnit test for getOrderDetailsByUserId method")
    @Test
    public void getOrderDetailsByUserId_ShouldReturnOrderDetails() {
        // Given
        long userId = 1L;
        when(orderDetailRepository.findByUserId(userId)).thenReturn(Collections.singletonList(orderDetail));
        when(orderDetailMapper.orderDetailToReturnOrderDto(orderDetail)).thenReturn(returnOrderDetailDto);

        // When
        List<ReturnOrderDetailDto> result = orderDetailService.getOrderDetailsByUserId(userId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnOrderDetailDto.getId());
    }
}
