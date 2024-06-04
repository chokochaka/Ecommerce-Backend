package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.rating.RatingDto;
import com.spring.ecommerce.mapper.RatingMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.Rating;
import com.spring.ecommerce.repositories.OrderRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.RatingRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final UserRepository userRepository;
    private final RatingMapper ratingMapper;
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;
    private final OrderRepository orderRepository;

    @Override
    public void createRating(RatingDto ratingDto) {
//        Long orderDetailId = orderRepository.findFirstOrderDetailIdForRating(ratingDto.getUserId(), ratingDto.getProductId());
//        if (orderDetailId == null) {
//            throw new RuntimeException("User can not comment");
//        }

        Product product = productRepository.findById(ratingDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Rating rating = ratingMapper.createRatingDtoToRating(ratingDto);
        rating.setProduct(product);
        ratingRepository.save(rating);
    }

    @Override
    public void editRating(long ratingId, RatingDto ratingDto) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Rating not found"));
        rating.setRatingValue(ratingDto.getRatingValue());
        rating.setReviewValue(ratingDto.getReviewValue());
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
