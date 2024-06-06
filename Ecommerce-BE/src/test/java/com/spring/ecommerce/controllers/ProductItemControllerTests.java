package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.ProductItemService;
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
@ContextConfiguration(classes = ProductItemController.class)
public class ProductItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductItemService productItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getProductItemsBySearch_ShouldReturnListOfProductItems() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnProductItemDto returnProductItemDto = ReturnProductItemDto.builder()
                .id(1L)
                .price(100.0)
                .availableStock(10)
                .build();
        List<ReturnProductItemDto> returnProductItemDtoList = Collections.singletonList(returnProductItemDto);
        when(productItemService.getProductItemsBySearch(any(SearchRequestDto.class))).thenReturn(returnProductItemDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/productItem/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnProductItemDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnProductItemDto.getId().intValue())));
    }

    @Test
    public void getProductItemsBySearchAndPagination_ShouldReturnPagedProductItems() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnProductItemDto returnProductItemDto = ReturnProductItemDto.builder()
                .id(1L)
                .price(100.0)
                .availableStock(10)
                .build();
        Page<ReturnProductItemDto> returnProductItemDtoPage = new PageImpl<>(Collections.singletonList(returnProductItemDto));
        when(productItemService.getProductItemsBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnProductItemDtoPage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/productItem/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnProductItemDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].id", is(returnProductItemDto.getId().intValue())));
    }

    @Test
    public void deleteProductItem_ShouldDeleteProductItem() throws Exception {
        // Given
        long productId = 1L;
        doNothing().when(productItemService).deleteProductItem(productId);

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/productItem/{id}", productId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getProductItemsByProductId_ShouldReturnListOfProductItems() throws Exception {
        // Given
        long productId = 1L;
        ReturnProductItemDto returnProductItemDto = ReturnProductItemDto.builder()
                .id(1L)
                .price(100.0)
                .availableStock(10)
                .build();
        List<ReturnProductItemDto> returnProductItemDtoList = Collections.singletonList(returnProductItemDto);
        when(productItemService.getProductItemsByProductId(anyLong())).thenReturn(returnProductItemDtoList);

        // When
        ResultActions response = mockMvc.perform(get("/api/v1/productItem/product/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnProductItemDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnProductItemDto.getId().intValue())));
    }
}
