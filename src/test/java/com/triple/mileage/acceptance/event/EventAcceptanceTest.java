package com.triple.mileage.acceptance.event;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.acceptance.AcceptanceTest;
import com.triple.mileage.event.presentation.dto.EventRequest;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.user.domain.User;
import com.triple.mileage.user.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("Event 인수테스트")
public class EventAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다.")
    void increasePoint() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "ADD", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다. - 없는 유저 id인 경우")
    void increasePointNoSuchUser() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "ADD", review.getUuid(), review.getContent(), photos, UUID.randomUUID(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다. - 없는 장소 id인 경우")
    void increasePointNoSuchPlace() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "ADD", review.getUuid(), review.getContent(), photos, user.getUuid(), UUID.randomUUID()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다. - 없는 리뷰 id인 경우")
    void increasePointNoSuchReview() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "ADD", UUID.randomUUID(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다. - 없는 이벤트 타입인 경우")
    void increasePointNoSuchEventType() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "lotto", "ADD", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("리뷰 생성시 포인트 적립 이벤트가 발생한다. - 없는 이벤트 액션인 경우")
    void increasePointNoSuchEventAction() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "read", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("리뷰 수정시 포인트 수정 이벤트가 발생한다.")
    void updatePoint() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "MOD", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("리뷰 삭제시 포인트 삭제 이벤트가 발생한다.")
    void decreasePoint() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place = placeRepository.save(new Place(UUID.randomUUID()));
        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user, place,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "DELETE", review.getUuid(), review.getContent(), photos, user.getUuid(), place.getUuid()
        );

        // when
        ExtractableResponse<Response> response = 이벤트_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 이벤트_요청(EventRequest request) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/events")
                          .then()
                          .log().all()
                          .extract();
    }
}
