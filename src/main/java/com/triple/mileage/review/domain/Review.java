package com.triple.mileage.review.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.user.domain.User;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Review {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    public Review(UUID id, String content, User user, Place place, List<ReviewImage> reviewImages) {
        this.id = id;
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
}
