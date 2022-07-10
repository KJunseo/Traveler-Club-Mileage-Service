package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.history.domain.PointHistory;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

public class UpdatePointEvent implements EventExecution {
    private final PointHistoryRepository pointHistoryRepository;

    public UpdatePointEvent(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public void execute(User user, Place place, Review review) {
        int prevBasicPoint = review.getBasicPoint();
        user.decreasePoint(prevBasicPoint);

        review.initBasicPoint();
        review.calculatePoint();

        int newBasicPoint = review.getBasicPoint();
        user.increasePoint(newBasicPoint);

        recordPointHistory(user, review, prevBasicPoint, newBasicPoint);
    }

    private void recordPointHistory(User user, Review review, int prevBasicPoint, int newBasicPoint) {
        int diff = newBasicPoint - prevBasicPoint;
        if (diff != 0) {
            PointHistory history = new PointHistory(user, review, PointType.CONTENT, diff);
            pointHistoryRepository.save(history);
        }
    }
}
