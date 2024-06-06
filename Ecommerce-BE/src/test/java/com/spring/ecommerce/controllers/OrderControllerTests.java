package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.CanUserComment;
import com.spring.ecommerce.dto.order.CreateOrderDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getOrdersBySearch_ShouldReturnListOfOrders() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnOrderDto returnOrderDto = ReturnOrderDto.builder()
                .id(1L)
                .build();
        List<ReturnOrderDto> returnOrderDtoList = Collections.singletonList(returnOrderDto);
        when(orderService.getOrdersBySearch(any(SearchRequestDto.class))).thenReturn(returnOrderDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/order/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnOrderDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnOrderDto.getId().intValue())));
    }

    @Test
    public void getOrdersBySearchAndPagination_ShouldReturnPagedOrders() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnOrderDto returnOrderDto = ReturnOrderDto.builder()
                .id(1L)
                .build();
        Page<ReturnOrderDto> returnOrderDtoPage = new PageImpl<>(Collections.singletonList(returnOrderDto));
        when(orderService.getOrdersBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnOrderDtoPage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/order/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnOrderDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].id", is(returnOrderDto.getId().intValue())));
    }

    @Test
    public void deleteOrder_ShouldDeleteOrder() throws Exception {
        // Given
        long orderId = 1L;
        doNothing().when(orderService).deleteOrder(anyLong());

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/order/{id}", orderId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void approveOrder_ShouldApproveOrder() throws Exception {
        // Given
        long orderId = 1L;
        doNothing().when(orderService).approveOrder(anyLong());

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/order/approve/{orderId}", orderId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void canUserComment_ShouldReturnOrderDetailId() throws Exception {
        // Given
        CanUserComment canUserComment = CanUserComment.builder()
                .userId(1L)
                .productId(1L)
                .build();
        when(orderService.canUserComment(any(CanUserComment.class))).thenReturn(1L);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/order/canUserComment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(canUserComment)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }
}
