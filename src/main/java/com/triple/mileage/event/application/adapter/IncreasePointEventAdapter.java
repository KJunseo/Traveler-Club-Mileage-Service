package com.triple.mileage.event.application.adapter;

import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.application.eventexecution.IncreasePointEvent;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

import org.springframework.stereotype.Component;

@Component
public class IncreasePointEventAdapter implements EventAdapter {
    private final IncreasePointEvent increasePointEvent;

    public IncreasePointEventAdapter(IncreasePointEvent increasePointEvent) {
        this.increasePointEvent = increasePointEvent;
    }

    @Override
    public boolean supports(EventType type, EventAction action) {
        return EventType.REVIEW.equals(type) && EventAction.ADD.equals(action);
    }

    @Override
    public EventExecution getEventExecution() {
        return increasePointEvent;
    }
}
