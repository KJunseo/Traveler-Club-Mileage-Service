package com.triple.mileage.review.domain;

import java.util.UUID;
import javax.persistence.*;

@Entity
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public void belongTo(Review review) {
        this.review = review;
    }

    public ReviewImage() {
    }

    public ReviewImage(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
