package com.triple.mileage.user.presentation.dto;

import java.util.UUID;

public class UserPointRequest {
    private UUID id;

    public UserPointRequest() {
    }

    public UserPointRequest(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
