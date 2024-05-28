package com.spring.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.enums.GlobalOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTests extends AbstractContainer {

    private static final String API_CATEGORIES_PATH = "/api/v1/category";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getParentCategoriesBySearch_ShouldReturnListOfCategories() {
        // Create a parent category first
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");
        categoryDto.setDescription("Electronic items");
        testRestTemplate.postForEntity(API_CATEGORIES_PATH + "/parent", new HttpEntity<>(categoryDto), Void.class);

        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setGlobalOperator(GlobalOperator.ALL);
        searchRequestDto.setFieldRequestDtos(Collections.emptyList());

        // when
        ResponseEntity<List<ReturnCategoryDto>> response = testRestTemplate.exchange(
                API_CATEGORIES_PATH + "/parent/search",
                POST,
                new HttpEntity<>(searchRequestDto),
                new ParameterizedTypeReference<>() {
                }
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
        assertThat(response.getBody().stream().anyMatch(c -> c.getParentCategoryName().equals("Electronics"))).isTrue();
    }

    @Test
    void createCategory_ShouldSaveCategory() {
        // Create a parent category first
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");
        categoryDto.setDescription("Electronic items");
        testRestTemplate.postForEntity(API_CATEGORIES_PATH + "/parent", new HttpEntity<>(categoryDto), Void.class);


        // given
        AddCategoryToParentDto addCategoryToParentDto = new AddCategoryToParentDto();
        addCategoryToParentDto.setName("Electronics 1");
        addCategoryToParentDto.setDescription("Electronic Items 1");
        addCategoryToParentDto.setParentCategoryId(1L);

        // when
        ResponseEntity<Void> response = testRestTemplate.exchange(
                API_CATEGORIES_PATH,
                POST,
                new HttpEntity<>(addCategoryToParentDto),
                Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);

        // Verify the category was created
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setGlobalOperator(GlobalOperator.ALL);
        searchRequestDto.setFieldRequestDtos(Collections.emptyList());

        ResponseEntity<List<ReturnCategoryDto>> allCategoriesResponse = testRestTemplate.exchange(
                API_CATEGORIES_PATH + "/search",
                POST,
                new HttpEntity<>(searchRequestDto),
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(allCategoriesResponse.getStatusCode()).isEqualTo(OK);
        assertThat(allCategoriesResponse.getBody()).isNotNull();
        assertThat(allCategoriesResponse.getBody().stream()
                .anyMatch(c -> c.getName().equals("Electronics 1"))).isTrue();
    }

}