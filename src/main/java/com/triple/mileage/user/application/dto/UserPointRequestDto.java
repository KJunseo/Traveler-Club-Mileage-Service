package com.triple.mileage.user.application.dto;

import java.util.UUID;

public class UserPointRequestDto {
    private UUID id;

    public UserPointRequestDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
