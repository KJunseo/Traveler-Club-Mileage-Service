package com.triple.mileage.common;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.domain.EventRepository;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewImageRepository;
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
    private final EventRepository eventRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReviewImageRepository reviewImageRepository;

    public BasicDataInserter(
            UserRepository userRepository,
            PlaceRepository placeRepository,
            ReviewRepository reviewRepository,
            EventRepository eventRepository,
            PointHistoryRepository pointHistoryRepository,
            ReviewImageRepository reviewImageRepository
    ) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.pointHistoryRepository = pointHistoryRepository;
        this.reviewImageRepository = reviewImageRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        deleteAll();

        User user = userRepository.save(new User(UUID.randomUUID(), 0));

        Place place = placeRepository.save(new Place(UUID.randomUUID()));

        Review review = new Review(
                UUID.randomUUID(),
                "?????????!",
                user,
                place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))
        );
        reviewRepository.save(review);
    }

    private void deleteAll() {
        eventRepository.deleteAll();
        reviewImageRepository.deleteAll();
        pointHistoryRepository.deleteAll();
        reviewRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }
}
