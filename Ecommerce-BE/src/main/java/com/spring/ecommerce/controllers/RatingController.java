package com.spring.ecommerce.controllers;

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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "Rating API")
@Slf4j
public class RatingController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @PostMapping
    public void createOrder(@RequestBody CreateOrderDto createOrderDto) {
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

    @PostMapping("/can-comment")
    public Long canUserComment(@RequestBody CanUserComment canUserComment) {
        Long orderDetailId = orderRepository.findFirstOrderDetailIdForRating(canUserComment.getUserId(), canUserComment.getProductId());
        log.info("Can user comment: " + orderDetailId);
        return orderDetailId;
//        orderRepository.canUserComment(canUserComment.getUserId(), canUserComment.getProductId());
    }
}
