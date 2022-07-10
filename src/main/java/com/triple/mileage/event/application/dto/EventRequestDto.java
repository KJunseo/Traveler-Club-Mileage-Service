package com.triple.mileage.event.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

public class EventRequestDto {
    private EventType type;
    private EventAction action;
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

    public EventRequestDto(
            String type,
            String action,
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId
    ) {
        this(
                EventType.getType(type),
                EventAction.getAction(action),
                reviewId,
                content,
                new ArrayList<>(attachedPhotoIds),
                userId,
                placeId
        );
    }

    public EventRequestDto(
            EventType type,
            EventAction action,
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

    public EventType getType() {
        return type;
    }

    public EventAction getAction() {
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
