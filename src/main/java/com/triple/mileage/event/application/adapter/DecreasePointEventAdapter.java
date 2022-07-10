package com.triple.mileage.event.application.adapter;

import com.triple.mileage.event.application.eventexecution.DecreasePointEvent;
import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

import org.springframework.stereotype.Component;

@Component
public class DecreasePointEventAdapter implements EventAdapter {
    private final DecreasePointEvent decreasePointEvent;

    public DecreasePointEventAdapter(DecreasePointEvent decreasePointEvent) {
        this.decreasePointEvent = decreasePointEvent;
    }

    @Override
    public boolean supports(EventType type, EventAction action) {
        return EventType.REVIEW.equals(type) && EventAction.DELETE.equals(action);
    }

    @Override
    public EventExecution getEventExecution() {
        return decreasePointEvent;
    }
}
