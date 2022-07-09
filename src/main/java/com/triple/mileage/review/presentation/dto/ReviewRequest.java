package com.triple.mileage.review.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewRequest {
    private String content;
    private List<UUID> attachedPhotoIds = new ArrayList<>();

    public ReviewRequest() {
    }

    public ReviewRequest(String content, List<UUID> attachedPhotoIds) {
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
    }

    public String getContent() {
        return content;
    }

    public List<UUID> getAttachedPhotoIds() {
        return attachedPhotoIds;
    }
}
