package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.mapper.OrderMapper;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.OrderRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        Order order = orderMapper.orderDtoToOrder(createOrderDto.getOrder());
        Product product = productRepository.findById(createOrderDto.getOrderDetails().getFirst().getProductId()).orElseThrow();

        List<OrderDetail> orderDetails = orderMapper.orderDetailDtosToOrderDetails(createOrderDto.getOrderDetails());
        order.setOrderDetails(orderDetails);
        orderDetails.forEach(orderDetail -> {
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
        });
        User user = userRepository.findById(createOrderDto.getOrder().getUserId()).orElseThrow();
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public long canUserComment(CanUserComment canUserComment) {
        Long orderDetailId = orderRepository.findFirstOrderDetailIdForRating(canUserComment.getUserId(), canUserComment.getProductId());
        log.info("Can user comment: {}", orderDetailId);
        if (orderDetailId == null) {
            return -1;
        }
        return orderDetailId;
    }
}
