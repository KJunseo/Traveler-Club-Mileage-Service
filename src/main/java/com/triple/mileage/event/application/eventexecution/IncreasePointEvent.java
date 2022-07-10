package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.history.domain.PointHistory;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

public class IncreasePointEvent implements EventExecution {
    private final PointHistoryRepository pointHistoryRepository;

    public IncreasePointEvent(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public void execute(User user, Place place, Review review) {
        review.calculatePoint();
        if (place.isFirstReview()) {
            review.increaseBonusPoint();
        }

        int basicPoint = review.getBasicPoint();
        int bonusPoint = review.getBonusPoint();
        user.increasePoint(basicPoint + bonusPoint);

        recordPointHistory(user, review, basicPoint, bonusPoint);
    }

    private void recordPointHistory(User user, Review review, int basicPoint, int bonusPoint) {
        if (basicPoint != 0) {
            PointHistory contentHistory = new PointHistory(user, review, PointType.CONTENT, basicPoint);
            pointHistoryRepository.save(contentHistory);
        }
        if (bonusPoint != 0) {
            PointHistory bonusHistory = new PointHistory(user, review, PointType.BONUS, bonusPoint);
            pointHistoryRepository.save(bonusHistory);
        }
    }
}
