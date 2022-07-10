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

    @Embedded
    private ReviewPoint reviewPoint;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    protected Review() {
    }

    public Review(String content, User user, Place place, List<ReviewImage> reviewImages) {
        this(null, content, user, place, reviewImages);
    }

    public Review(UUID uuid, String content, User user, Place place, List<ReviewImage> reviewImages) {
        this(null, uuid, content, user, place, new ReviewPoint(), reviewImages);
    }

    public Review(UUID uuid, String content, User user, Place place, ReviewPoint reviewPoint, List<ReviewImage> reviewImages) {
        this(null, uuid, content, user, place, reviewPoint, reviewImages);
    }

    public Review(Long id, UUID uuid, String content, User user, Place place, ReviewPoint reviewPoint, List<ReviewImage> reviewImages) {
        this.id = id;
        this.uuid = uuid;
        this.content = content;
        this.user = user;
        this.place = place;
        this.reviewPoint = reviewPoint;
        this.reviewImages = reviewImages;
        reviewImages.forEach(image -> image.belongTo(this));
    }

    public void calculatePoint() {
        if (this.content.length() >= 1) {
            reviewPoint.increaseContentPoint();
        }
        if (reviewImages.size() >= 1) {
            reviewPoint.increasePhotoPoint();
        }
    }

    public void increaseBonusPoint() {
        reviewPoint.increaseBonusPoint();
    }

    public void initPoint() {
        reviewPoint.init();
    }

    public void initBasicPoint() {
        reviewPoint.initBasicPoint();
    }

    public int getBasicPoint() {
        return reviewPoint.basicPoint();
    }

    public int getBonusPoint() {
        return reviewPoint.bonusPoint();
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
