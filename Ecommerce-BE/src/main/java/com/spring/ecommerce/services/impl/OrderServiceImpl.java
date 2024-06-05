package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
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
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.OrderService;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final FilterSpecificationService<Order> orderFilterSpecificationService;

    private final OrderMapper orderMapper;

    @Override
    public List<ReturnOrderDto> getOrdersBySearch(SearchRequestDto searchRequestDto) {
        Specification<Order> orderSearchSpecification = orderFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return orderRepository.findAll(orderSearchSpecification).stream()
                .map(orderMapper::orderToReturnOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReturnOrderDto> getOrdersBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<Order> orderSearchSpecification = orderFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        Page<Order> orderPage = orderRepository.findAll(orderSearchSpecification, pageable);
        return orderPage.map(orderMapper::orderToReturnOrderDto);
    }

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        Order order = orderMapper.orderDtoToOrder(createOrderDto.getOrder());
        Product product = productRepository.findById(createOrderDto.getOrderDetails().getFirst().getProductId()).orElseThrow();

        List<OrderDetail> orderDetails = orderMapper.orderDetailDtosToOrderDetails(createOrderDto.getOrderDetails());
        order.setOrderDetails(orderDetails);
        order.setOrderUserId(createOrderDto.getOrder().getUserId());
        order.setOrderProductId(createOrderDto.getOrderDetails().getFirst().getProductId());
        orderDetails.forEach(orderDetail -> {
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setOrderDetailProductId(createOrderDto.getOrderDetails().getFirst().getProductId());
        });
        User user = userRepository.findById(createOrderDto.getOrder().getUserId()).orElseThrow();
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public long canUserComment(CanUserComment canUserComment) {
        List<Long> orderDetailId = orderRepository.findFirstOrderDetailIdForRating(canUserComment.getUserId(), canUserComment.getProductId());
        if (orderDetailId.isEmpty()) {
            return -1;
        }
        return orderDetailId.getFirst();
    }

    @Override
    public void approveOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setApproved(true);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
