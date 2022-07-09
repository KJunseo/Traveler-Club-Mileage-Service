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
        int originalPoint = user.getPoint();
        user.initPoint();

        List<PointHistory> histories = pointHistoryRepository.findAllByUserAndReview(user, review);
        int totalBonusPoint = histories.stream()
                                       .filter(PointHistory::isBonus)
                                       .mapToInt(PointHistory::getPoint)
                                       .sum();

        if (totalBonusPoint > 0) {
            originalPoint--;
            PointHistory history = new PointHistory(user, review, PointType.BONUS, -1);
            pointHistoryRepository.save(history);
        }

        if (originalPoint != 0) {
            PointHistory history = new PointHistory(user, review, PointType.CONTENT, originalPoint * -1);
            pointHistoryRepository.save(history);
        }
    }
}
