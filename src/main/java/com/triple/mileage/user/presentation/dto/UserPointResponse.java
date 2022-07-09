package com.triple.mileage.user.presentation.dto;

import com.triple.mileage.user.application.dto.UserPointResponseDto;

public class UserPointResponse {
    private int point;

    public UserPointResponse() {
    }

    public UserPointResponse(UserPointResponseDto responseDto) {
        this.point = responseDto.getPoint();
    }

    public int getPoint() {
        return point;
    }
}
