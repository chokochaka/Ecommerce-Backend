package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.AddCategoryToParentDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.CategoryMapper;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ParentCategoryRepository;
import com.spring.ecommerce.services.CategoryService;
import com.spring.ecommerce.services.FilterSpecificationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parent;
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
    private final FilterSpecificationService<ParentCategory> parentCategoryFilterSpecificationService;
    private final CategoryMapper categoryMapper;

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
        Specification<ParentCategory> parentCategorySearchSpecification = parentCategoryFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return parentCategoryRepository.findAll(parentCategorySearchSpecification);
    }

    @Override
    public Page<ParentCategory> getParentCategoriesBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<ParentCategory> parentCategorySearchSpecification = parentCategoryFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        return parentCategoryRepository.findAll(parentCategorySearchSpecification, pageable);
    }

    @Override
    public void createParentCategory(CategoryDto categoryDto) {
        ParentCategory parentCategory = categoryMapper.categoryDtoToCategory(categoryDto);
        parentCategoryRepository.save(parentCategory);
    }

    @Override
    public void deleteParentCategory(long id) {
        parentCategoryRepository.deleteById(id);
    }

    @Override
    public void createCategory(AddCategoryToParentDto addCategoryToParentDto) {
        Category category = categoryMapper.addCategoryToParentDtoToCategory(addCategoryToParentDto);
        ParentCategory parentCategory = parentCategoryRepository.findById(addCategoryToParentDto.getParentCategoryId()).orElseThrow();
        category.setParentCategory(parentCategory);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteProductCategoriesByCategoryId(id);
        categoryRepository.deleteById(id);
    }
}
