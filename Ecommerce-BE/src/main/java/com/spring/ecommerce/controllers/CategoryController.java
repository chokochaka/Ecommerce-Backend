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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReturnCategoryDto>> getCategoriesBySearch(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        List<ReturnCategoryDto> categories = categoryService.getCategoriesBySearch(searchRequestDto);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnCategoryDto>> getCategoriesBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        Page<ReturnCategoryDto> categories = categoryService.getCategoriesBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/parent/search")
    public ResponseEntity<List<ReturnCategoryDto>> getParentCategoriesBySearch(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        List<ReturnCategoryDto> categories = categoryService.getParentCategoriesBySearch(searchRequestDto);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/parent/search/paginated")
    public ResponseEntity<Page<ReturnCategoryDto>> getParentCategoriesBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        Page<ReturnCategoryDto> categories = categoryService.getParentCategoriesBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @Valid @RequestBody AddCategoryToParentDto addCategoryToParentDto
    ) {
        categoryService.addCategoryToParentCategory(addCategoryToParentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/parent")
    public ResponseEntity<Void> createParentCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        categoryService.createParentCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/parent/{id}")
    public ResponseEntity<Void> updateParentCategory(
            @PathVariable long id,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        categoryService.updateParentCategory(id, categoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable long id,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        categoryService.updateCategory(id, categoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/parent/{id}")
    public ResponseEntity<Void> deleteParentCategory(@PathVariable long id) {
        categoryService.deleteParentCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
