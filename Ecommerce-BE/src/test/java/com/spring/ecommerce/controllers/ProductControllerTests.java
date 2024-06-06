package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.ProductService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getProductItemsBySearch_ShouldReturnListOfProducts() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnProductDto returnProductDto = ReturnProductDto.builder()
                .id(1L)
                .build();
        List<ReturnProductDto> returnProductDtoList = Collections.singletonList(returnProductDto);
        when(productService.getProductsBySearch(any(SearchRequestDto.class))).thenReturn(returnProductDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnProductDtoList.size())))
                .andExpect(jsonPath("$[0].id", is(returnProductDto.getId().intValue())));
    }

    @Test
    public void getProductsBySearchAndPagination_ShouldReturnPagedProducts() throws Exception {
        // Given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnProductDto returnProductDto = ReturnProductDto.builder()
                .id(1L)
                .build();
        Page<ReturnProductDto> returnProductDtoPage = new PageImpl<>(Collections.singletonList(returnProductDto));
        when(productService.getProductsBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnProductDtoPage);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/product/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnProductDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].id", is(returnProductDto.getId().intValue())));
    }

    @Test
    public void createProduct_ShouldCreateProduct() throws Exception {
        // Given
        ProductDto productDto = ProductDto.builder()
                .name("Product Name")
                .description("Product Description")
                .price(100.0)
                .isFeatured(true)
                .imageUrl("imageUrl")
                .categoryIds(Collections.singleton(1L))
                .build();
        doNothing().when(productService).createProduct(any(ProductDto.class));

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct_ShouldUpdateProduct() throws Exception {
        // Given
        long productId = 1L;
        ProductDto productDto = ProductDto.builder()
                .name("Updated Product Name")
                .description("Updated Product Description")
                .price(150.0)
                .isFeatured(true)
                .imageUrl("updatedImageUrl")
                .categoryIds(Collections.singleton(1L))
                .build();
        doNothing().when(productService).updateProduct(anyLong(), any(ProductDto.class));

        // When
        ResultActions response = mockMvc.perform(put("/api/v1/product/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct_ShouldDeleteProduct() throws Exception {
        // Given
        long productId = 1L;
        doNothing().when(productService).deleteProduct(anyLong());

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/product/{id}", productId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getProductsByCategoryName_ShouldReturnListOfProducts() throws Exception {
        // Given
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Category Name")
                .build();
        ReturnProductDto returnProductDto = ReturnProductDto.builder()
                .id(1L)
                .name("Product Name")
                .build();
        List<ReturnProductDto> returnProductDtoList = Collections.singletonList(returnProductDto);
        when(productService.getProductsByCategoryName(any(String.class))).thenReturn(returnProductDtoList);

        // When
        ResultActions response = mockMvc.perform(post("/api/v1/product/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnProductDtoList.size())))
                .andExpect(jsonPath("$[0].name", is(returnProductDto.getName())));
    }
}
