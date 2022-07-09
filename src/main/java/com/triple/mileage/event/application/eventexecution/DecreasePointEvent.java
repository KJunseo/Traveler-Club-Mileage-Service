package com.triple.mileage.event.application.eventexecution;

import java.util.List;

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
        int reviewPoint = review.getPoint();

        if (reviewPoint == 0) {
            return;
        }

        user.decreasePoint(reviewPoint);
        PointHistory history = new PointHistory(user, review, PointType.BONUS, reviewPoint * -1);
        pointHistoryRepository.save(history);

        List<PointHistory> histories = pointHistoryRepository.findAllByUserAndReview(user, review);
        boolean existBonus = histories.stream()
                                      .filter(PointHistory::isBonus)
                                      .anyMatch(PointHistory::isPositive);
        if (existBonus) {
            user.decreasePoint(1);
            PointHistory decreaseBonus = new PointHistory(user, review, PointType.BONUS, -1);
            pointHistoryRepository.save(decreaseBonus);
        }
    }
}
