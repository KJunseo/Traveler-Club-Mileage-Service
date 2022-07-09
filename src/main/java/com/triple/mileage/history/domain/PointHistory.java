package com.triple.mileage.history.domain;

import java.util.UUID;
import javax.persistence.*;

import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    public int getPoint() {
        return point;
    }
}
