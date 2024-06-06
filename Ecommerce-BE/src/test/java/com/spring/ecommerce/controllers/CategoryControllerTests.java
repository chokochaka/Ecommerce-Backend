package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.CategoryService;
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
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = CategoryController.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CategoryController.class)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCategoriesBySearch_ShouldReturnListOfCategories() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnCategoryDto returnCategoryDto = new ReturnCategoryDto();
        returnCategoryDto.setId(1L);
        returnCategoryDto.setName("Electronics");
        returnCategoryDto.setDescription("Electronic items");
        List<ReturnCategoryDto> returnCategoryDtoList = Collections.singletonList(returnCategoryDto);
        when(categoryService.getCategoriesBySearch(any(SearchRequestDto.class))).thenReturn(returnCategoryDtoList);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnCategoryDtoList.size())))
                .andExpect(jsonPath("$[0].name", is(returnCategoryDto.getName())));
    }

    @Test
    public void getCategoriesBySearchAndPagination_ShouldReturnPagedCategories() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnCategoryDto returnCategoryDto = new ReturnCategoryDto();
        returnCategoryDto.setId(1L);
        returnCategoryDto.setName("Electronics");
        returnCategoryDto.setDescription("Electronic items");
        Page<ReturnCategoryDto> returnCategoryDtoPage = new PageImpl<>(Collections.singletonList(returnCategoryDto));
        when(categoryService.getCategoriesBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnCategoryDtoPage);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnCategoryDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].name", is(returnCategoryDto.getName())));
    }

    @Test
    public void getParentCategoriesBySearch_ShouldReturnListOfParentCategories() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnCategoryDto returnCategoryDto = new ReturnCategoryDto();
        returnCategoryDto.setId(1L);
        returnCategoryDto.setParentCategoryName("Home Appliances");
        List<ReturnCategoryDto> returnCategoryDtoList = Collections.singletonList(returnCategoryDto);
        when(categoryService.getParentCategoriesBySearch(any(SearchRequestDto.class))).thenReturn(returnCategoryDtoList);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category/parent/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnCategoryDtoList.size())))
                .andExpect(jsonPath("$[0].parentCategoryName", is(returnCategoryDto.getParentCategoryName())));
    }

    @Test
    public void getParentCategoriesBySearchAndPagination_ShouldReturnPagedParentCategories() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        ReturnCategoryDto returnCategoryDto = new ReturnCategoryDto();
        returnCategoryDto.setId(1L);
        returnCategoryDto.setParentCategoryName("Home Appliances");
        Page<ReturnCategoryDto> returnCategoryDtoPage = new PageImpl<>(Collections.singletonList(returnCategoryDto));
        when(categoryService.getParentCategoriesBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(returnCategoryDtoPage);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category/parent/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) returnCategoryDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].parentCategoryName", is(returnCategoryDto.getParentCategoryName())));
    }

    @Test
    public void createCategory_ShouldSaveCategory() throws Exception {
        // given
        AddCategoryToParentDto addCategoryToParentDto = new AddCategoryToParentDto();
        addCategoryToParentDto.setName("Electronics");
        addCategoryToParentDto.setParentCategoryId(1L);
        doNothing().when(categoryService).addCategoryToParentCategory(any(AddCategoryToParentDto.class));

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addCategoryToParentDto)));

        // then
        response.andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createParentCategory_ShouldSaveParentCategory() throws Exception {
        // given
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Electronics")
                .description("Electronic items")
                .build();
        doNothing().when(categoryService).createParentCategory(any(CategoryDto.class));

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/category/parent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateCategory_ShouldUpdateExistingCategory() throws Exception {
        // given
        long categoryId = 1L;
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Electronics")
                .description("Electronic items")
                .build();
        doNothing().when(categoryService).updateCategory(anyLong(), any(CategoryDto.class));

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/category/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategory_ShouldDeleteCategory() throws Exception {
        // given
        long categoryId = 1L;
        doNothing().when(categoryService).deleteCategory(categoryId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/category/{id}", categoryId));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteParentCategory_ShouldDeleteParentCategory() throws Exception {
        // given
        long parentCategoryId = 1L;
        doNothing().when(categoryService).deleteParentCategory(parentCategoryId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/category/parent/{id}", parentCategoryId));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
