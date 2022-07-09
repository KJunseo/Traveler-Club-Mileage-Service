package com.triple.mileage.event.domain;

import java.util.Arrays;

public enum EventType {
    REVIEW("review");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public static EventType getType(String type) {
        return Arrays.stream(EventType.values())
                     .filter(t -> t.value.equals(type))
                     .findFirst()
                     .orElseThrow();
    }
}
