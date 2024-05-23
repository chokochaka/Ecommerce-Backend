package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.AddCategoryToParentDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category source);

    CategoryDto categoryToCategoryDto(ParentCategory source);

    ParentCategory categoryDtoToCategory(CategoryDto source);

    Category addCategoryToParentDtoToCategory(AddCategoryToParentDto source);
}
