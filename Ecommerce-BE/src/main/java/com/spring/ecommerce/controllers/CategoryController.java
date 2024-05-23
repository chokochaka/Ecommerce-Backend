package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.AddCategoryToParentDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import com.spring.ecommerce.services.CategoryService;
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

    @PostMapping("/parent/search")
    public List<ParentCategory> getParentCategoriesBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getParentCategoriesBySearch(searchRequestDto);
    }

    @PostMapping("/parent/search/paginated")
    public Page<ParentCategory> getParentCategoriesBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return categoryService.getParentCategoriesBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createCategory(@RequestBody AddCategoryToParentDto addCategoryToParentDto) {
        categoryService.createCategory(addCategoryToParentDto);
    }

    @PostMapping("/parent")
    public void createParentCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createParentCategory(categoryDto);
    }

    @PutMapping("/{id}")
    public void updateCategory(
            @PathVariable long id,
            @RequestBody CategoryDto categoryDto
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
