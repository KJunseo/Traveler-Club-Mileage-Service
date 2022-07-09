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
        int point = review.getPoint();
        user.increasePoint(point);
        PointHistory contentHistory = new PointHistory(user, review, PointType.CONTENT, point);
        pointHistoryRepository.save(contentHistory);

        if (place.isFirstReview()) {
            user.increasePoint(1);
            PointHistory bonusHistory = new PointHistory(user, review, PointType.BONUS, 1);
            pointHistoryRepository.save(bonusHistory);
        }
    }
}
