package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.OrderDetailDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.OrderMapper;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.OrderRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FilterSpecificationService<Order> orderFilterSpecificationService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private ReturnOrderDto returnOrderDto;
    private SearchRequestDto searchRequestDto;
    private CreateOrderDto createOrderDto;
    private OrderDetail orderDetail;
    private OrderDetailDto orderDetailDto;
    private User user;
    private Product product;

    @BeforeEach
    public void setup() {
        order = new Order();
        order.setId(1L);

        orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrder(order);

        orderDetailDto = OrderDetailDto.builder()
                .productId(1L)
                .build();

        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        returnOrderDto = ReturnOrderDto.builder()
                .id(1L)
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());

        createOrderDto = CreateOrderDto.builder()
                .orderDetails(Collections.singletonList(orderDetailDto))
                .build();
    }

    @AfterEach
    public void tearDown() {
        reset(orderRepository, userRepository, productRepository, orderFilterSpecificationService, orderMapper);
    }

    @DisplayName("JUnit test for getOrdersBySearch method")
    @Test
    public void getOrdersBySearch_ShouldReturnOrders() {
        // Given
        Specification<Order> specification = mock(Specification.class);
        when(orderFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(orderRepository.findAll(specification)).thenReturn(Collections.singletonList(order));
        when(orderMapper.orderToReturnOrderDto(order)).thenReturn(returnOrderDto);

        // When
        List<ReturnOrderDto> result = orderService.getOrdersBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnOrderDto.getId());
    }

    @DisplayName("JUnit test for canUserComment method")
    @Test
    public void canUserComment_ShouldReturnOrderDetailId() {
        // Given
        CanUserComment canUserComment = CanUserComment.builder()
                .userId(1L)
                .productId(1L)
                .build();
        when(orderRepository.findFirstOrderDetailIdForRating(canUserComment.getUserId(), canUserComment.getProductId()))
                .thenReturn(Collections.singletonList(1L));

        // When
        long result = orderService.canUserComment(canUserComment);

        // Then
        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("JUnit test for approveOrder method")
    @Test
    public void approveOrder_ShouldSetOrderApproved() {
        // Given
        long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When
        orderService.approveOrder(orderId);

        // Then
        assertThat(order.isApproved()).isTrue();
        verify(orderRepository, times(1)).save(order);
    }

    @DisplayName("JUnit test for deleteOrder method")
    @Test
    public void deleteOrder_ShouldDeleteOrder() {
        // Given
        long orderId = 1L;

        // When
        orderService.deleteOrder(orderId);

        // Then
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
