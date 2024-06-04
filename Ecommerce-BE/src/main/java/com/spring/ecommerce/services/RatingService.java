package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.rating.RatingDto;

public interface RatingService {
    void createRating(RatingDto ratingDto);

    void editRating(long ratingId, RatingDto ratingDto);

    void deleteRating(long ratingId);
}
