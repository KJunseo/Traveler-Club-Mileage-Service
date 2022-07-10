package com.triple.mileage.integration.event;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.triple.mileage.event.application.EventService;
import com.triple.mileage.event.application.dto.EventRequestDto;
import com.triple.mileage.integration.IntegrationTest;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.application.ReviewService;
import com.triple.mileage.review.application.dto.ReviewRequestDto;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.user.application.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용이 비어있고 리뷰 사진이 없고, 첫 번째 리뷰도 아닌 경우 0점")
    void emptyReview() {
        // given
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto dto1 = reviewSaveEvent(user1, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));
        EventRequestDto dto2 = reviewSaveEvent(user2, "", Collections.emptyList());

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        User user = userService.findByUuid(user2.getUuid());

        // then
        assertThat(user.getPoint()).isEqualTo(0);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용만 있는 경우 1점")
    void onlyContent() {
        // given
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto dto1 = reviewSaveEvent(user1, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));
        EventRequestDto dto2 = reviewSaveEvent(user2, "좋아요!", Collections.emptyList());

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        User user = userService.findByUuid(user2.getUuid());

        // then
        assertThat(user.getPoint()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 사진만 있는 경우 1점")
    void onlyPhoto() {
        // given
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto dto1 = reviewSaveEvent(user1, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));
        EventRequestDto dto2 = reviewSaveEvent(user2, "",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        User user = userService.findByUuid(user2.getUuid());

        // then
        assertThat(user.getPoint()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용, 사진 모두 있는 경우 2점")
    void contentAndPhoto() {
        // given
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto dto1 = reviewSaveEvent(user1, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));
        EventRequestDto dto2 = reviewSaveEvent(user2, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));

        // when
        eventService.progress(dto1);
        eventService.progress(dto2);
        User user = userService.findByUuid(user2.getUuid());

        // then
        assertThat(user.getPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰 생성시 포인트가 적립된다. - 리뷰 내용, 사진 모두 있는 경우 & 첫번째 리뷰인 경우 3점")
    void contentAndPhotoAndFirstReview() {
        // given
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto requestDto = reviewSaveEvent(user, "좋아요!",
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));

        entityManager.flush();
        entityManager.clear();

        // when
        eventService.progress(requestDto);
        User result = userService.findByUuid(user.getUuid());

        // then
        assertThat(result.getPoint()).isEqualTo(3);
    }

    @Test
    @DisplayName("리뷰 수정시 포인트가 수정된다. - 1점(보너스) -> 2점(내용 + 보너스)")
    void updatePoint() {
        // given
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto prevDto = reviewSaveEvent(user, "", Collections.emptyList());

        entityManager.flush();
        entityManager.clear();

        eventService.progress(prevDto); // 리뷰 생성 후 포인트 적립 이벤트 진행
        User prev = userService.findByUuid(user.getUuid());
        int prevPoint = prev.getPoint();

        ReviewRequestDto requestDto = new ReviewRequestDto(prevDto.getReviewId(), "좋아요!", Collections.emptyList());
        reviewService.update(requestDto);

        EventRequestDto curDto = new EventRequestDto(
                "REVIEW", "MOD", prevDto.getReviewId(), requestDto.getContent(),
                Collections.emptyList(), user.getUuid(), prevDto.getPlaceId()
        );

        // when
        eventService.progress(curDto);  // 리뷰 수정 후 포인트 수정 이벤트 진행
        User cur = userService.findByUuid(user.getUuid());
        int curPoint = cur.getPoint();

        // then
        assertThat(prevPoint).isEqualTo(1);
        assertThat(curPoint).isEqualTo(2);
    }

    @Test
    @DisplayName("리뷰 삭제시 포인트가 삭제된다.")
    void decreasePoint() {
        // given
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        EventRequestDto prevDto = reviewSaveEvent(user, "좋아요!", Collections.emptyList());

        entityManager.flush();
        entityManager.clear();

        eventService.progress(prevDto); // 리뷰 생성 후 포인트 적립 이벤트 진행
        User prev = userService.findByUuid(user.getUuid());
        int prevPoint = prev.getPoint();

        EventRequestDto curDto = new EventRequestDto(
                "REVIEW", "DELETE", prevDto.getReviewId(), "좋아요!",
                Collections.emptyList(), user.getUuid(), prevDto.getPlaceId()
        );

        // when
        eventService.progress(curDto);
        User cur = userService.findByUuid(user.getUuid());
        int curPoint = cur.getPoint();

        // then
        assertThat(prevPoint).isEqualTo(2);
        assertThat(curPoint).isEqualTo(0);
    }

    private EventRequestDto reviewSaveEvent(User user, String content, List<ReviewImage> images) {
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), content, user, place, images));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());
        return new EventRequestDto(
                "REVIEW", "ADD", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );
    }
}
