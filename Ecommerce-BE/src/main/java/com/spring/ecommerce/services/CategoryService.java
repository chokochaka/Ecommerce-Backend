package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<ReturnCategoryDto> getCategoriesBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnCategoryDto> getCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto);

    List<ReturnCategoryDto> getParentCategoriesBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnCategoryDto> getParentCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createParentCategory(CategoryDto categoryDto);

    void deleteParentCategory(long id);

    void addCategoryToParentCategory(AddCategoryToParentDto AddCategoryToParentDto);

    void updateCategory(long id, CategoryDto categoryDto);

    void deleteCategory(long id);

    void updateParentCategory(long id, CategoryDto categoryDto);
}
