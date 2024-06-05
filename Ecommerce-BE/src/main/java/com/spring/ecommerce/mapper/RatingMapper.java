package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.rating.RatingDto;
import com.spring.ecommerce.dto.rating.ReturnRatingDto;
import com.spring.ecommerce.models.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    Rating createRatingDtoToRating(RatingDto ratingDto);

    ReturnRatingDto ratingToReturnRatingDto(Rating rating);
}
