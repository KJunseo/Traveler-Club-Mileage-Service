package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.history.domain.PointHistory;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

public class DecreasePointEvent implements EventExecution {
    private final PointHistoryRepository pointHistoryRepository;

    public DecreasePointEvent(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public void execute(User user, Place place, Review review) {
        int basicPoint = review.getBasicPoint();
        int bonusPoint = review.getBonusPoint();
        user.decreasePoint(basicPoint + bonusPoint);
        review.initPoint();

        recordPointHistory(user, review, basicPoint, bonusPoint);
    }

    private void recordPointHistory(User user, Review review, int basicPoint, int bonusPoint) {
        if (basicPoint != 0) {
            PointHistory history = new PointHistory(user, review, PointType.CONTENT, basicPoint * -1);
            pointHistoryRepository.save(history);
        }
        if (bonusPoint != 0) {
            PointHistory history = new PointHistory(user, review, PointType.BONUS, bonusPoint * -1);
            pointHistoryRepository.save(history);
        }
    }
}
