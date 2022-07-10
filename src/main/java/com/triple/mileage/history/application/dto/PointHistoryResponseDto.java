package com.triple.mileage.history.application.dto;

import java.util.UUID;

import com.triple.mileage.review.domain.PointType;

public class PointHistoryResponseDto {
    private UUID userId;
    private UUID reviewId;
    private String type;
    private int point;

    public PointHistoryResponseDto(UUID userId, UUID reviewId, PointType type, int point) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.type = type.name();
        this.point = point;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public String getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }
}
