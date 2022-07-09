package com.triple.mileage.event.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventRequest {
    private String type;
    private String action;
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds = new ArrayList<>();
    private UUID userId;
    private UUID placeId;

    public EventRequest() {
    }

    public EventRequest(
            String type,
            String action,
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId
    ) {
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.userId = userId;
        this.placeId = placeId;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public String getContent() {
        return content;
    }

    public List<UUID> getAttachedPhotoIds() {
        return attachedPhotoIds;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPlaceId() {
        return placeId;
    }
}
