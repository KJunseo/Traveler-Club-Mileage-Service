package com.triple.mileage.history.domain;

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

    public boolean isBonus() {
        return PointType.BONUS.equals(type);
    }

    public boolean isPositive() {
        return this.point > 0;
    }

    public int getPoint() {
        return point;
    }
}
