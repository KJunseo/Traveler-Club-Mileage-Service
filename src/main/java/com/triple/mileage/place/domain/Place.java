package com.triple.mileage.place.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.Review;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    public Place() {
    }

    public Place(UUID uuid) {
        this(uuid, new ArrayList<>());
    }

    public Place(UUID uuid, List<Review> reviews) {
        this.uuid = uuid;
        this.reviews = reviews;
    }

    public boolean isFirstReview() {
        return reviews.size() == 1;
    }

    public UUID getUuid() {
        return uuid;
    }
}
