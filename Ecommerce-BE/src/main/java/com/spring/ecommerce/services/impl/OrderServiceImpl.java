package com.spring.ecommerce.services.impl;

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
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.MailService;
import com.spring.ecommerce.services.OrderService;
import jakarta.mail.MessagingException;
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
    private final MailService mailService;

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

        // Map order details from DTO to entity
        List<OrderDetail> orderDetails = orderMapper.orderDetailDtosToOrderDetails(createOrderDto.getOrderDetails());
        order.setOrderDetails(orderDetails);
        order.setOrderUserId(createOrderDto.getOrder().getUserId());

        // Map each OrderDetailDto to its corresponding OrderDetail and set product ID
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail orderDetail = orderDetails.get(i);
            OrderDetailDto orderDetailDto = createOrderDto.getOrderDetails().get(i);

            Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setOrderDetailProductId(orderDetailDto.getProductId());
            // You might need to set additional fields for OrderDetail here if required
        }

        // Retrieve the user and associate it with the order
        User user = userRepository.findById(createOrderDto.getOrder().getUserId()).orElseThrow();
        order.setUser(user);

        // Save the order with its details
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
    public void approveOrder(long orderId) throws MessagingException {
        Order order = orderRepository.findById(orderId).orElseThrow();
        mailService.sendNotificationOrderApproved(order.getUser().getEmail(), order.getCommodityCode());
        order.setApproved(true);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
