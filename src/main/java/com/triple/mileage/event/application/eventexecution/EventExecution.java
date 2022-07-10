package com.triple.mileage.event.application.eventexecution;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

public interface EventExecution {

    void execute(User user, Place place, Review review);

    boolean isSame(EventExecution eventExecution);
}
