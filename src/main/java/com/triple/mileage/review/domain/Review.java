package com.triple.mileage.review.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.user.domain.User;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    protected Review() {
    }

    public Review(String content, User user, Place place, List<ReviewImage> reviewImages) {
        this(null, content, user, place, reviewImages);
    }

    public Review(UUID uuid, String content, User user, Place place, List<ReviewImage> reviewImages) {
        this.uuid = uuid;
        this.content = content;
        this.user = user;
        this.place = place;
        this.reviewImages = reviewImages;
        reviewImages.forEach(image -> image.belongTo(this));
    }

    public int getPoint() {
        int point = 0;
        if (this.content.length() >= 1) {
            point++;
        }
        if (reviewImages.size() >= 1) {
            point++;
        }
        return point;
    }

    public void update(String content, List<ReviewImage> reviewImages) {
        this.content = content;
        this.reviewImages = reviewImages;
        reviewImages.forEach(image -> image.belongTo(this));
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getContent() {
        return content;
    }

    public List<ReviewImage> getReviewImages() {
        return reviewImages;
    }
}
