package com.triple.mileage.review.domain;

import java.util.UUID;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ReviewImage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public void belongTo(Review review) {
        this.review = review;
    }

    public UUID getId() {
        return id;
    }
}
