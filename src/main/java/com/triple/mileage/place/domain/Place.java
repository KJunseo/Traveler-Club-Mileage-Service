package com.triple.mileage.place.domain;

import java.util.ArrayList;
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

    public boolean isFirstReview() {
        return reviews.isEmpty();
    }

    public UUID getId() {
        return id;
    }
}
