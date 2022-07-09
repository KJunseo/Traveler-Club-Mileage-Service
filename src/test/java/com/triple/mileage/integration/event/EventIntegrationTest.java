package com.triple.mileage.integration.event;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.triple.mileage.event.application.EventService;
import com.triple.mileage.event.application.dto.EventRequestDto;
import com.triple.mileage.integration.IntegrationTest;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.user.domain.User;
import com.triple.mileage.user.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Event 통합 테스트")
public class EventIntegrationTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventService eventService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용이 비어있고 리뷰 사진이 없고, 첫 번째 리뷰도 아닌 경우 0점")
    void emptyReview() {
        // given
        User user1 = userRepository.save(new User(0));
        User user2 = userRepository.save(new User(0));
        EventRequestDto dto1 = eventRequest(user1, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));
        EventRequestDto dto2 = eventRequest(user2, "", Collections.emptyList());

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        Optional<User> result = userRepository.findById(user2.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPoint()).isEqualTo(0);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용만 있는 경우 1점")
    void onlyContent() {
        // given
        User user1 = userRepository.save(new User(0));
        User user2 = userRepository.save(new User(0));
        EventRequestDto dto1 = eventRequest(user1, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));
        EventRequestDto dto2 = eventRequest(user2, "좋아요!", Collections.emptyList());

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        Optional<User> result = userRepository.findById(user2.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPoint()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 사진만 있는 경우 1점")
    void onlyPhoto() {
        // given
        User user1 = userRepository.save(new User(0));
        User user2 = userRepository.save(new User(0));
        EventRequestDto dto1 = eventRequest(user1, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));
        EventRequestDto dto2 = eventRequest(user2, "", List.of(new ReviewImage(), new ReviewImage()));

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        Optional<User> result = userRepository.findById(user2.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPoint()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용, 사진 모두 있는 경우 2점")
    void contentAndPhoto() {
        // given
        User user1 = userRepository.save(new User(0));
        User user2 = userRepository.save(new User(0));
        EventRequestDto dto1 = eventRequest(user1, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));
        EventRequestDto dto2 = eventRequest(user2, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        Optional<User> result = userRepository.findById(user2.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용, 사진 모두 있는 경우 & 첫번째 리뷰인 경우 3점")
    void contentAndPhotoAndFirstReview() {
        // given
        User user = userRepository.save(new User(0));
        EventRequestDto requestDto = eventRequest(user, "좋아요!", List.of(new ReviewImage(), new ReviewImage()));

        entityManager.flush();
        entityManager.clear();

        // when
        eventService.progress(requestDto);
        Optional<User> result = userRepository.findById(user.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPoint()).isEqualTo(3);
    }

    private EventRequestDto eventRequest(User user, String content, List<ReviewImage> images) {
        Place place = placeRepository.save(new Place());
        Review review = reviewRepository.save(new Review(content, user, place, images));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getId).collect(Collectors.toList());
        return new EventRequestDto(
                "REVIEW", "ADD", review.getId(), review.getContent(), photos, user.getId(), place.getId()
        );
    }
}
