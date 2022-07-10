package com.triple.mileage.review.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.exception.review.NoSuchReviewException;
import com.triple.mileage.review.application.dto.ReviewRequestDto;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewImageRepository;
import com.triple.mileage.review.domain.ReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewImageRepository reviewImageRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
    }

    @Transactional(readOnly = true)
    public Review findByUuid(UUID reviewId) {
        return reviewRepository.findByUuid(reviewId)
                               .orElseThrow(NoSuchReviewException::new);
    }

    @Transactional
    public void update(ReviewRequestDto requestDto) {
        Review review = findByUuid(requestDto.getId());
        reviewImageRepository.deleteByReview(review);
        List<ReviewImage> reviewImages = requestDto.getAttachedPhotoIds()
                                                   .stream()
                                                   .map(ReviewImage::new)
                                                   .collect(Collectors.toList());

        review.update(requestDto.getContent(), reviewImages);
    }
}
