package com.triple.mileage.event.application.adapter;

import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

public interface EventAdapter {
    boolean supports(EventType type, EventAction action);

    EventExecution getEventExecution();
}
