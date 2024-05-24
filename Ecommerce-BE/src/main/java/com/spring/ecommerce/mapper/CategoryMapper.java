package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.ParentCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    ReturnCategoryDto CategoryToReturnCategoryDto(Category source);

    ReturnCategoryDto CategoryToReturnCategoryDto(ParentCategory source);

    ParentCategory categoryDtoToCategory(CategoryDto source);

    Category addCategoryToParentDtoToCategory(AddCategoryToParentDto source);
}
