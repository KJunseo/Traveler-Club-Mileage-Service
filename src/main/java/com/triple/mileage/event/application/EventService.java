package com.triple.mileage.event.application;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.application.dto.EventRequestDto;
import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.domain.Event;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventRepository;
import com.triple.mileage.event.domain.EventType;
import com.triple.mileage.place.application.PlaceService;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.application.ReviewService;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.application.UserService;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final EventActionAdapterService eventActionAdapterService;
    private final List<EventExecution> eventExecutions;

    public EventService(
            EventRepository eventRepository,
            UserService userService,
            PlaceService placeService,
            ReviewService reviewService,
            EventActionAdapterService eventActionAdapterService,
            List<EventExecution> eventExecutions
    ) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.placeService = placeService;
        this.reviewService = reviewService;
        this.eventActionAdapterService = eventActionAdapterService;
        this.eventExecutions = eventExecutions;
    }

    @Transactional
    public void progress(EventRequestDto requestDto) {
        User user = userService.findByUuid(requestDto.getUserId());
        Place place = placeService.findByUuid(requestDto.getPlaceId());
        Review review = reviewService.findByUuid(requestDto.getReviewId());

        EventType type = requestDto.getType();
        EventAction action = requestDto.getAction();
        EventExecution eventExecution = eventActionAdapterService.getEventHandler(type, action);
        EventExecution execution = getEventExecution(eventExecution);
        execution.execute(user, place, review);

        Event event = new Event(UUID.randomUUID(), type, action, review);
        eventRepository.save(event);
    }

    private EventExecution getEventExecution(EventExecution eventExecution) {
        return eventExecutions.stream()
                              .filter(e -> e.isSame(eventExecution))
                              .findFirst()
                              .orElseThrow();
    }
}
