package com.triple.mileage.review.application;

import java.util.UUID;

import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Review findById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                               .orElseThrow();
    }
}
