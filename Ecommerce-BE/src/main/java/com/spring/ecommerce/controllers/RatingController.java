package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.rating.RatingDto;
import com.spring.ecommerce.services.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "Rating API")
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public void createRating(@RequestBody RatingDto ratingDto) {
        ratingService.createRating(ratingDto);
    }

    @PutMapping("")
    public void editRating(@RequestBody RatingDto ratingDto) {
        ratingService.editRating(ratingDto.getId(), ratingDto);
    }

    @DeleteMapping("/{ratingId}")
    public void deleteRating(@PathVariable long ratingId) {
        ratingService.deleteRating(ratingId);
    }

}
