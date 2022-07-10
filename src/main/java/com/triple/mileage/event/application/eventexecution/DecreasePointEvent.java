package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.common.aop.Record;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Component;

@Component
public class DecreasePointEvent implements EventExecution {

    @Record(action = EventAction.DELETE)
    @Override
    public void execute(User user, Place place, Review review) {
        int basicPoint = review.getBasicPoint();
        int bonusPoint = review.getBonusPoint();
        user.decreasePoint(basicPoint + bonusPoint);
        review.initPoint();
    }

    @Override
    public boolean isSame(EventExecution eventExecution) {
        return eventExecution instanceof DecreasePointEvent;
    }
}
