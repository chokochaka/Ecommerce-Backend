package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.search.FieldRequestDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.CategoryMapper;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ParentCategoryRepository;
import com.spring.ecommerce.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceTests.class);
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ParentCategoryRepository parentCategoryRepository;

    @Mock
    private FilterSpecificationService<Category> categoryFilterSpecificationService;

    @Mock
    private FilterSpecificationService<ParentCategory> parentCategoryFilterSpecificationService;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private ParentCategory parentCategory;
    private CategoryDto categoryDto;
    private AddCategoryToParentDto addCategoryToParentDto;
    private ReturnCategoryDto returnCategoryDto;
    private ReturnCategoryDto returnParentCategoryDto;
    private SearchRequestDto searchRequestDto;
    private List<FieldRequestDto> fieldRequestDtos;
    private PageRequestDto pageRequestDto;
    private Specification specification;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic items");

        returnCategoryDto = new ReturnCategoryDto();
        returnCategoryDto.setId(1L);
        returnCategoryDto.setName("Electronics");
        returnCategoryDto.setDescription("Electronic items");

        parentCategory = new ParentCategory();
        parentCategory.setId(1L);
        parentCategory.setParentCategoryName("Home Appliances");

        returnParentCategoryDto = new ReturnCategoryDto();
        returnParentCategoryDto.setId(1L);
        returnParentCategoryDto.setParentCategoryName("Home Appliances");

        categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");
        categoryDto.setDescription("Electronic items");

        addCategoryToParentDto = new AddCategoryToParentDto();
        addCategoryToParentDto.setName("Electronics");
        addCategoryToParentDto.setParentCategoryId(1L);

        fieldRequestDtos = Collections.singletonList(new FieldRequestDto());
        pageRequestDto = new PageRequestDto();
        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(pageRequestDto);
        searchRequestDto.setFieldRequestDtos(fieldRequestDtos);

        specification = any(Specification.class);
    }

    @AfterEach
    void tearDown() {
        reset(categoryRepository, parentCategoryRepository,
                categoryFilterSpecificationService, parentCategoryFilterSpecificationService,
                categoryMapper);
    }

    @DisplayName("JUnit test for getCategoriesBySearch method")
    @Test
    public void getCategoriesBySearch_ShouldReturnListOfCategories() {
        // Given
        List<Category> categoryList = Collections.singletonList(category);
        lenient().when(categoryFilterSpecificationService.getSearchSpecification(fieldRequestDtos, any())).thenReturn(specification);
        doReturn(categoryList).when(categoryRepository).findAll(eq(specification));

        when(categoryMapper.CategoryToReturnCategoryDto(any(Category.class))).thenReturn(returnCategoryDto);

        // When
        List<ReturnCategoryDto> result = categoryService.getCategoriesBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(returnCategoryDto.getName());
        verify(categoryRepository, times(1)).findAll(eq(specification));
    }

    @DisplayName("JUnit test for getCategoriesBySearchAndPagination method")
    @Test
    public void getCategoriesBySearchAndPagination_ShouldReturnPagedCategories() {
        // Given
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));

        lenient().when(categoryFilterSpecificationService.getSearchSpecification(fieldRequestDtos, any())).thenReturn(specification);
        doReturn(categoryPage).when(categoryRepository).findAll(eq(specification), any(Pageable.class));
        when(categoryMapper.CategoryToReturnCategoryDto(any(Category.class))).thenReturn(returnCategoryDto);

        // When
        Page<ReturnCategoryDto> result = categoryService.getCategoriesBySearchAndPagination(searchRequestDto);

        // Then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(returnCategoryDto.getName());
        verify(categoryRepository, times(1)).findAll(eq(specification), any(Pageable.class));
    }


    @DisplayName("JUnit test for getParentCategoriesBySearch method")
    @Test
    public void getParentCategoriesBySearch_ShouldReturnListOfParentCategories() {
        // Given
        List<ParentCategory> parentCategoryList = Collections.singletonList(parentCategory);
        lenient().when(parentCategoryFilterSpecificationService.getSearchSpecification(fieldRequestDtos, any())).thenReturn(specification);
        doReturn(parentCategoryList).when(parentCategoryRepository).findAll(eq(specification));
        when(categoryMapper.CategoryToReturnCategoryDto(any(ParentCategory.class))).thenReturn(returnParentCategoryDto);

        // When
        List<ReturnCategoryDto> result = categoryService.getParentCategoriesBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParentCategoryName()).isEqualTo(returnParentCategoryDto.getParentCategoryName());
        verify(parentCategoryRepository, times(1)).findAll(eq(specification));
    }

    @DisplayName("JUnit test for getParentCategoriesBySearchAndPagination method")
    @Test
    public void getParentCategoriesBySearchAndPagination_ShouldReturnPagedParentCategories() {
        // Given
        Page<ParentCategory> parentCategoryPage = new PageImpl<>(Collections.singletonList(parentCategory));

        lenient().when(parentCategoryFilterSpecificationService.getSearchSpecification(fieldRequestDtos, any())).thenReturn(specification);
        doReturn(parentCategoryPage).when(parentCategoryRepository).findAll(eq(specification), any(Pageable.class));
        when(categoryMapper.CategoryToReturnCategoryDto(any(ParentCategory.class))).thenReturn(returnParentCategoryDto);

        // When
        Page<ReturnCategoryDto> result = categoryService.getParentCategoriesBySearchAndPagination(searchRequestDto);

        // Then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(returnParentCategoryDto.getName());
        verify(parentCategoryRepository, times(1)).findAll(eq(specification), any(Pageable.class));
    }

    @DisplayName("JUnit test for deleteParentCategory method")
    @Test
    public void deleteParentCategory_ShouldDeleteParentCategory() {
        // Given
        long id = 1L;

        // When
        categoryService.deleteParentCategory(id);

        // Then
        verify(parentCategoryRepository, times(1)).deleteById(id);
    }

    @DisplayName("JUnit test for deleteCategory method")
    @Test
    public void deleteCategory_ShouldDeleteCategory() {
        // Given
        long id = 1L;

        // When
        categoryService.deleteCategory(id);

        // Then
        verify(categoryRepository, times(1)).deleteProductCategoriesByCategoryId(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }
}
