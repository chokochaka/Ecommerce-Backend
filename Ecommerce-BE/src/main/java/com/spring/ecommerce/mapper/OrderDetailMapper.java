package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.models.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    ReturnOrderDetailDto orderDetailToReturnOrderDto(OrderDetail source);
}
