package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.AddCategoryToParentDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ParentCategoryRepository;
import com.spring.ecommerce.services.CategoryService;
import com.spring.ecommerce.services.FilterSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final FilterSpecificationService<Category> categoryFilterSpecificationService;

    @Override
    public List<Category> getCategoriesBySearch(SearchRequestDto searchRequestDto) {
        Specification<Category> categorySearchSpecification = categoryFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return categoryRepository.findAll(categorySearchSpecification);
    }

    @Override
    public Page<Category> getCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<Category> categorySearchSpecification = categoryFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        return categoryRepository.findAll(categorySearchSpecification, pageable);
    }

    @Override
    public List<ParentCategory> getParentCategoriesBySearch(SearchRequestDto searchRequestDto) {
        return List.of();
    }

    @Override
    public Page<ParentCategory> getParentCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto) {
        return null;
    }

    @Override
    public void createParentCategory(CategoryDto categoryDto) {

    }

    @Override
    public void updateParentCategory(long id, CategoryDto categoryDto) {

    }

    @Override
    public void deleteParentCategory(long id) {

    }

    @Override
    public void createCategory(AddCategoryToParentDto AddCategoryToParentDto) {

    }

    @Override
    public void updateCategory(long id, CategoryDto categoryDto) {

    }

    @Override
    public void deleteCategory(long id) {

    }
}
