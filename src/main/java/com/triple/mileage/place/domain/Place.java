package com.triple.mileage.place.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.Review;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Place {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    public Place() {
    }

    public Place(UUID id) {
        this(id, new ArrayList<>());
    }

    public Place(UUID id, List<Review> reviews) {
        this.id = id;
        this.reviews = reviews;
    }

    public boolean isFirstReview() {
        return reviews.size() == 1;
    }

    public UUID getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
