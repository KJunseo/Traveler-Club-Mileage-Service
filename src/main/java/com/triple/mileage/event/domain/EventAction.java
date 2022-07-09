package com.triple.mileage.event.domain;

import java.util.Arrays;

public enum EventAction {
    ADD("add"),
    MOD("mod"),
    DELETE("delete");

    private final String value;

    EventAction(String value) {
        this.value = value;
    }

    public static EventAction getAction(String action) {
        return Arrays.stream(EventAction.values())
                     .filter(a -> a.value.equals(action))
                     .findFirst()
                     .orElseThrow();
    }
}
