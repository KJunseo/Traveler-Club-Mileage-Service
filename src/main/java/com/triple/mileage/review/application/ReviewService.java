package com.triple.mileage.review.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.exception.review.NoSuchReviewException;
import com.triple.mileage.review.application.dto.ReviewRequestDto;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
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
                               .orElseThrow(NoSuchReviewException::new);
    }

    @Transactional
    public void update(ReviewRequestDto requestDto) {
        Review review = findById(requestDto.getId());

        List<ReviewImage> reviewImages = requestDto.getAttachedPhotoIds().stream()
                                                   .map(ReviewImage::new)
                                                   .collect(Collectors.toList());

        review.update(requestDto.getContent(), reviewImages);
    }
}
