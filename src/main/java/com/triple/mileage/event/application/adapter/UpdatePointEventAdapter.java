package com.triple.mileage.event.application.adapter;

import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.application.eventexecution.UpdatePointEvent;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

import org.springframework.stereotype.Component;

@Component
public class UpdatePointEventAdapter implements EventAdapter {
    private final UpdatePointEvent updatePointEvent;

    public UpdatePointEventAdapter(UpdatePointEvent updatePointEvent) {
        this.updatePointEvent = updatePointEvent;
    }

    @Override
    public boolean supports(EventType type, EventAction action) {
        return EventType.REVIEW.equals(type) && EventAction.MOD.equals(action);
    }

    @Override
    public EventExecution getEventExecution() {
        return updatePointEvent;
    }
}
