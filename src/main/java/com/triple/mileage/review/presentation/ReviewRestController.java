package com.triple.mileage.review.presentation;

import java.util.UUID;

import com.triple.mileage.review.application.ReviewService;
import com.triple.mileage.review.application.dto.ReviewRequestDto;
import com.triple.mileage.review.presentation.dto.ReviewRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewRestController {
    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PatchMapping("/reviews/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable UUID id, @RequestBody ReviewRequest request) {
        ReviewRequestDto requestDto = new ReviewRequestDto(id, request.getContent(), request.getAttachedPhotoIds());
        reviewService.update(requestDto);
        return ResponseEntity.ok().build();
    }
}
