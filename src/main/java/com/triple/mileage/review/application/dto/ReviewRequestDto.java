package com.triple.mileage.review.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewRequestDto {
    private UUID id;
    private String content;
    private List<UUID> attachedPhotoIds;

    public ReviewRequestDto(UUID id, String content, List<UUID> attachedPhotoIds) {
        this.id = id;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<UUID> getAttachedPhotoIds() {
        return attachedPhotoIds;
    }
}
