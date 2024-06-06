package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.rating.RatingDto;
import com.spring.ecommerce.mapper.RatingMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.Rating;
import com.spring.ecommerce.repositories.OrderRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.RatingRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.impl.RatingServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating;
    private Product product;
    private RatingDto ratingDto;

    @BeforeEach
    public void setup() {
        rating = new Rating();
        rating.setId(1L);

        product = new Product();
        product.setId(1L);

        rating.setProduct(product);

        ratingDto = RatingDto.builder()
                .productId(1L)
                .ratingValue(4)
                .reviewValue("Great product!")
                .build();
    }

    @AfterEach
    public void tearDown() {
        reset(userRepository, ratingMapper, productRepository, ratingRepository, orderRepository);
    }

    @DisplayName("JUnit test for createRating method")
    @Test
    public void createRating_ShouldCreateRating() {
        // Given
        when(productRepository.findById(ratingDto.getProductId())).thenReturn(Optional.of(product));
        when(ratingMapper.createRatingDtoToRating(ratingDto)).thenReturn(rating);

        // When
        ratingService.createRating(ratingDto);

        // Then
        ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository, times(1)).save(ratingArgumentCaptor.capture());
        Rating savedRating = ratingArgumentCaptor.getValue();

        assertThat(savedRating).isNotNull();
        assertThat(savedRating.getProduct().getId()).isEqualTo(ratingDto.getProductId());
    }

    @DisplayName("JUnit test for editRating method")
    @Test
    public void editRating_ShouldEditRating() {
        // Given
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        when(productRepository.findById(rating.getProduct().getId())).thenReturn(Optional.of(product));

        // When
        ratingService.editRating(1L, ratingDto);

        // Then
        ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository, times(1)).save(ratingArgumentCaptor.capture());
        Rating updatedRating = ratingArgumentCaptor.getValue();

        assertThat(updatedRating).isNotNull();
        assertThat(updatedRating.getRatingValue()).isEqualTo(ratingDto.getRatingValue());
        assertThat(updatedRating.getReviewValue()).isEqualTo(ratingDto.getReviewValue());
    }

    @DisplayName("JUnit test for deleteRating method")
    @Test
    public void deleteRating_ShouldDeleteRating() {
        // Given
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        when(productRepository.findById(rating.getProduct().getId())).thenReturn(Optional.of(product));

        // When
        ratingService.deleteRating(1L);

        // Then
        verify(ratingRepository, times(1)).deleteById(1L);
    }
}
