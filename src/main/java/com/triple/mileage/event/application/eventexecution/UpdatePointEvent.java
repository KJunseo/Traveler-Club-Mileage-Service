package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.common.aop.Record;
import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Component;

@Component
public class UpdatePointEvent implements EventExecution {

    @Record(action = EventAction.MOD)
    @Override
    public void execute(User user, Place place, Review review) {
        int prevBasicPoint = review.getBasicPoint();
        user.decreasePoint(prevBasicPoint);

        review.initBasicPoint();
        review.calculatePoint();

        int newBasicPoint = review.getBasicPoint();
        user.increasePoint(newBasicPoint);
    }

    @Override
    public boolean isSame(EventExecution eventExecution) {
        return eventExecution instanceof UpdatePointEvent;
    }
}
