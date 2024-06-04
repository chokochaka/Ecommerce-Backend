package com.spring.ecommerce.dto.rating;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RatingDto {
    private long ratingId;
    private long productId;
    private long userId;
    private String userName;
    private long orderDetailId;
    private int ratingValue;
    private String reviewValue;
}
