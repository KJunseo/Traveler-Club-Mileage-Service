package com.triple.mileage.event.application.eventexecution;

import java.util.List;

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
        int originalPoint = user.getPoint();
        int curPoint = 0;

        user.initPoint();

        boolean isBonus = pointHistoryRepository.existsByUserAndReviewAndTypeEquals(user, review, PointType.BONUS);
        if (isBonus) {
            user.increasePoint(1);
            curPoint++;
        }

        int point = review.getPoint();
        user.increasePoint(point);
        curPoint += point;

        int diff = curPoint - originalPoint;
        if (diff != 0) {
            PointHistory history = new PointHistory(user, review, PointType.CONTENT, diff);
            pointHistoryRepository.save(history);
        }
    }
}
