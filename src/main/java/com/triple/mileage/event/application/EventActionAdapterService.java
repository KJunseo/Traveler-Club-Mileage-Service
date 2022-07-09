package com.triple.mileage.event.application;

import java.util.List;

import com.triple.mileage.event.application.adapter.EventAdapter;
import com.triple.mileage.event.application.eventexecution.EventExecution;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.event.domain.EventType;

import org.springframework.stereotype.Service;

@Service
public class EventActionAdapterService {
    private final List<EventAdapter> adapters;

    public EventActionAdapterService(List<EventAdapter> adapters) {
        this.adapters = adapters;
    }

    public EventExecution getEventHandler(EventType type, EventAction action) {
        EventAdapter eventAdapter = adapters.stream()
                                            .filter(adapter -> adapter.supports(type, action))
                                            .findFirst()
                                            .orElseThrow();
        return eventAdapter.getEventExecution();
    }
}
