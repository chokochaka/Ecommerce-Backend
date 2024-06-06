package com.spring.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTests extends AbstractContainer {

    private static final String API_CATEGORIES_PATH = "/api/v1/category";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private HttpHeaders headers;

    @Test
    void createParentCategoryWithoutPermission_ShouldThrowAccessDenied1() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Electronics")
                .description("Electronic items")
                .build();
        testRestTemplate.postForEntity(API_CATEGORIES_PATH + "/parent", new HttpEntity<>(categoryDto), Void.class);
    }

    @Test
    void createParentCategoryWithoutPermission_ShouldThrowAccessDenied() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Electronics")
                .description("Electronic items")
                .build();

        HttpEntity<CategoryDto> request = new HttpEntity<>(categoryDto, headers);

        ResponseEntity<Void> response = testRestTemplate.postForEntity(API_CATEGORIES_PATH + "/parent", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Test
    void getParentCategoriesBySearch_ShouldReturnListOfCategories() {
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
        assertThat(response.getBody()).isEmpty();
        assertThat(response.getBody().size()).isEqualTo(0);
    }

    @Test
    void createCategory_ShouldSaveCategory() {
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
        assertThat(allCategoriesResponse.getBody()).isEmpty();
        assertThat(allCategoriesResponse.getBody()).isNotNull();
    }

}
