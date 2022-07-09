package com.triple.mileage.common;

import java.util.List;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.user.domain.User;
import com.triple.mileage.user.domain.UserRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BasicDataInserter implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public BasicDataInserter(UserRepository userRepository, PlaceRepository placeRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        User user = userRepository.save(new User(0));

        Place place = placeRepository.save(new Place());

        Review review = new Review("좋아요!", user, place, List.of(new ReviewImage(), new ReviewImage()));
        reviewRepository.save(review);
    }

}