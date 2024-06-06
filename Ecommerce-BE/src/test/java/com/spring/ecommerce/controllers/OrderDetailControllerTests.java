package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.OrderDetailService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = OrderDetailController.class)
public class OrderDetailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getOrdersBySearch_ShouldReturnListOfOrderDetails() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnOrderDetailDto returnOrderDetailDto = ReturnOrderDetailDto.builder()
                .id(1L)
                .build();
        List<ReturnOrderDetailDto> returnOrderDetailDtoList = Collections.singletonList(returnOrderDetailDto);
        when(orderDetailService.getOrderDetailsBySearch(any(SearchRequestDto.class))).thenReturn(returnOrderDetailDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/orderDetail/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnOrderDetailDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnOrderDetailDto.getId().intValue())));
    }

    @Test
    public void getOrdersBySearchAndPagination_ShouldReturnPagedOrderDetails() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnOrderDetailDto returnOrderDetailDto = ReturnOrderDetailDto.builder()
                .id(1L)
                .build();
        Page<ReturnOrderDetailDto> returnOrderDetailDtoPage = new PageImpl<>(Collections.singletonList(returnOrderDetailDto));
        when(orderDetailService.getOrderDetailsBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnOrderDetailDtoPage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/orderDetail/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnOrderDetailDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].id", is(returnOrderDetailDto.getId().intValue())));
    }

    @Test
    public void deleteOrderDetail_ShouldDeleteOrderDetail() throws Exception {
        // Given
        long orderDetailId = 1L;
        doNothing().when(orderDetailService).deleteOrderDetail(anyLong());

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/orderDetail/{id}", orderDetailId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOrdersByUserId_ShouldReturnListOfOrderDetails() throws Exception {
        // Given
        long userId = 1L;
        ReturnOrderDetailDto returnOrderDetailDto = ReturnOrderDetailDto.builder()
                .id(1L)
                .build();
        List<ReturnOrderDetailDto> returnOrderDetailDtoList = Collections.singletonList(returnOrderDetailDto);
        when(orderDetailService.getOrderDetailsByUserId(anyLong())).thenReturn(returnOrderDetailDtoList);

        // When
        ResultActions response = mockMvc.perform(get("/api/v1/orderDetail/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnOrderDetailDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnOrderDetailDto.getId().intValue())));
    }
}
