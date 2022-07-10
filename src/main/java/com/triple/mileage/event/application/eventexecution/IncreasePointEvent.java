package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.common.aop.Record;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Component;

@Component
public class IncreasePointEvent implements EventExecution {

    @Record(action = EventAction.ADD)
    @Override
    public void execute(User user, Place place, Review review) {
        review.calculatePoint();
        if (place.isFirstReview(user)) {
            review.increaseBonusPoint();
        }

        int basicPoint = review.getBasicPoint();
        int bonusPoint = review.getBonusPoint();
        user.increasePoint(basicPoint + bonusPoint);
    }

    @Override
    public boolean isSame(EventExecution eventExecution) {
        return eventExecution instanceof IncreasePointEvent;
    }
}
