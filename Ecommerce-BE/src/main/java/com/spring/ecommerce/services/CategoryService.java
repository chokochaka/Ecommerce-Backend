package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.AddCategoryToParentDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoriesBySearch(SearchRequestDto searchRequestDto);

    Page<Category> getCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto);

    List<ParentCategory> getParentCategoriesBySearch(SearchRequestDto searchRequestDto);

    Page<ParentCategory> getParentCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createParentCategory(CategoryDto categoryDto);

    void deleteParentCategory(long id);

    void createCategory(AddCategoryToParentDto AddCategoryToParentDto);

    void updateCategory(long id, CategoryDto categoryDto);

    void deleteCategory(long id);
}
