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
        List<PointHistory> histories = pointHistoryRepository.findAllByUserAndReview(user, review);
        int prevReviewPoint = histories.stream()
                                       .mapToInt(PointHistory::getPoint)
                                       .sum();
        int newReviewPoint = review.getPoint();

        user.decreasePoint(prevReviewPoint);
        user.increasePoint(newReviewPoint);

        boolean existBonus = histories.stream()
                                      .filter(PointHistory::isBonus)
                                      .anyMatch(PointHistory::isPositive);

        if (existBonus) {
            user.increasePoint(1);
        }

        int diff = newReviewPoint - prevReviewPoint;
        if (diff != 0) {
            PointHistory history = new PointHistory(user, review, PointType.CONTENT, diff);
            pointHistoryRepository.save(history);
        }
    }
}
