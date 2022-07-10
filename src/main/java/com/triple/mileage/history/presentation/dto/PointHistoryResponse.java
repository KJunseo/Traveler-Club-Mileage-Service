package com.triple.mileage.history.presentation.dto;

import java.util.UUID;

import com.triple.mileage.history.application.dto.PointHistoryResponseDto;

public class PointHistoryResponse {
    private UUID userId;
    private UUID reviewId;
    private String type;
    private int point;

    public PointHistoryResponse() {
    }

    public PointHistoryResponse(PointHistoryResponseDto responseDto) {
        this.userId = responseDto.getUserId();
        this.reviewId = responseDto.getReviewId();
        this.type = responseDto.getType();
        this.point = responseDto.getPoint();
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
