package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/search")
    public List<ReturnCategoryDto> getCategoriesBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getCategoriesBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ReturnCategoryDto> getCategoriesBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getCategoriesBySearchAndPagination(searchRequestDto);
    }

    @PostMapping("/parent/search")
    public List<ReturnCategoryDto> getParentCategoriesBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getParentCategoriesBySearch(searchRequestDto);
    }

    @PostMapping("/parent/search/paginated")
    public Page<ReturnCategoryDto> getParentCategoriesBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getParentCategoriesBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createCategory(@Valid @RequestBody AddCategoryToParentDto addCategoryToParentDto) {
        categoryService.addCategoryToParentCategory(addCategoryToParentDto);
    }

    @PostMapping("/parent")
    public void createParentCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.createParentCategory(categoryDto);
    }

    @PutMapping("/parent/{id}")
    public void updateParentCategory(
            @PathVariable long id,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        categoryService.updateParentCategory(id, categoryDto);
    }

    @PutMapping("/{id}")
    public void updateCategory(
            @PathVariable long id,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
    }

    @DeleteMapping("/parent/{id}")
    public void deleteParentCategory(@PathVariable long id) {
        categoryService.deleteParentCategory(id);
    }

}
