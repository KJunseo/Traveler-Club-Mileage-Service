package com.triple.mileage.event.presentation;

import com.triple.mileage.event.application.EventService;
import com.triple.mileage.event.application.dto.EventRequestDto;
import com.triple.mileage.event.presentation.dto.EventRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventRestController {
    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    public ResponseEntity<Void> event(@RequestBody EventRequest request) {
        EventRequestDto requestDto = new EventRequestDto(
                request.getType(),
                request.getAction(),
                request.getReviewId(),
                request.getContent(),
                request.getAttachedPhotoIds(),
                request.getUserId(),
                request.getPlaceId()
        );
        eventService.progress(requestDto);
        return ResponseEntity.ok().build();
    }
}
