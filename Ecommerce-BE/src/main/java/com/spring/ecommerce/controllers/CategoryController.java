package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/search")
    public List<Category> getCategoriesBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getCategoriesBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<Category> getCategoriesBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getCategoriesBySearchAndPagination(searchRequestDto);
    }

}
