package com.triple.mileage.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    void deleteByReview(Review review);
}
