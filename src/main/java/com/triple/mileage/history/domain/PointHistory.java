package com.triple.mileage.history.domain;

import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

@Entity(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @Enumerated(EnumType.STRING)
    private PointType type;

    private int point;

    protected PointHistory() {
    }

    public PointHistory(User user, Review review, PointType type, int point) {
        this.user = user;
        this.review = review;
        this.type = type;
        this.point = point;
    }

    public UUID getUserUuid() {
        return user.getUuid();
    }

    public UUID getReviewUuid() {
        return review.getUuid();
    }

    public PointType getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }
}
