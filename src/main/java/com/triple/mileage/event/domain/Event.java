package com.triple.mileage.event.domain;

import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.Review;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Event {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Enumerated(EnumType.STRING)
    private EventAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    protected Event() {
    }

    public Event(EventType type, EventAction action, Review review) {
        this.type = type;
        this.action = action;
        this.review = review;
    }
}
