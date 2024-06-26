package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.order.OrderDetailDto;
import com.spring.ecommerce.dto.order.OrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto order);

    Order orderDetailDtoToOrderDetail(OrderDetailDto order);

    List<OrderDetail> orderDetailDtosToOrderDetails(List<OrderDetailDto> orderDetailDtos);

    @Mapping(target = "approved", source = "approved")
    ReturnOrderDto orderToReturnOrderDto(Order order);
}
