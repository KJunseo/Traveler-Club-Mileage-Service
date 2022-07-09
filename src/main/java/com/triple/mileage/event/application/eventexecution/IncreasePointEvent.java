package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

public class IncreasePointEvent implements EventExecution {

    @Override
    public void execute(User user, Place place, Review review) {
        int point = review.getPoint();
        user.increasePoint(point);
        if (place.isFirstReview()) {
            user.increasePoint(1);
        }
    }
}
