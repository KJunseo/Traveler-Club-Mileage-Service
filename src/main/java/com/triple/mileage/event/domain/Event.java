package com.triple.mileage.event.domain;

import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.Review;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Enumerated(EnumType.STRING)
    private EventAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    protected Event() {
    }

    public Event(UUID uuid, EventType type, EventAction action, Review review) {
        this.uuid = uuid;
        this.type = type;
        this.action = action;
        this.review = review;
    }
}
