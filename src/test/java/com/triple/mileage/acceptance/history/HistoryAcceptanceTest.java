package com.triple.mileage.acceptance.history;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.acceptance.AcceptanceTest;
import com.triple.mileage.event.presentation.dto.EventRequest;
import com.triple.mileage.history.presentation.dto.PointHistoryResponse;
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
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.triple.mileage.acceptance.event.EventAcceptanceTest.이벤트_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("포인트 내역 인수 테스트")
public class HistoryAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("전체 포인트 증감 내역 조회")
    void allPointHistories() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place1 = placeRepository.save(new Place(UUID.randomUUID()));
        Place place2 = placeRepository.save(new Place(UUID.randomUUID()));

        Review review1 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user1, place1,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos1 = review1.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());
        EventRequest request1 = new EventRequest(
                "REVIEW", "ADD", review1.getUuid(), review1.getContent(), photos1, user1.getUuid(), place1.getUuid()
        );
        이벤트_요청(request1);

        Review review2 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user1, place2,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos2 = review2.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());
        EventRequest request2 = new EventRequest(
                "REVIEW", "ADD", review2.getUuid(), review2.getContent(), photos2, user1.getUuid(), place2.getUuid()
        );
        이벤트_요청(request2);

        Review review3 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user2, place1, Collections.emptyList()));
        EventRequest request3 = new EventRequest(
                "REVIEW", "ADD", review3.getUuid(), review3.getContent(), Collections.emptyList(), user2.getUuid(), place1
                .getUuid()
        );
        이벤트_요청(request3);

        // when
        ExtractableResponse<Response> response = 전체_포인트_내역_조회();
        List<PointHistoryResponse> result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(5);
    }

    @Test
    @DisplayName("유저별 포인트 증감 내역 조회")
    void userPointHistories() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user1 = userRepository.save(new User(UUID.randomUUID(), 0));
        User user2 = userRepository.save(new User(UUID.randomUUID(), 0));
        Place place1 = placeRepository.save(new Place(UUID.randomUUID()));
        Place place2 = placeRepository.save(new Place(UUID.randomUUID()));

        Review review1 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user1, place1,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos1 = review1.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());
        EventRequest request1 = new EventRequest(
                "REVIEW", "ADD", review1.getUuid(), review1.getContent(), photos1, user1.getUuid(), place1.getUuid()
        );
        이벤트_요청(request1);

        Review review2 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user1, place2,
                List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID()))));
        List<UUID> photos2 = review2.getReviewImages().stream().map(ReviewImage::getUuid).collect(Collectors.toList());
        EventRequest request2 = new EventRequest(
                "REVIEW", "ADD", review2.getUuid(), review2.getContent(), photos2, user1.getUuid(), place2.getUuid()
        );
        이벤트_요청(request2);

        Review review3 = reviewRepository.save(new Review(UUID.randomUUID(), "좋아요!", user2, place1, Collections.emptyList()));
        EventRequest request3 = new EventRequest(
                "REVIEW", "ADD", review3.getUuid(), review3.getContent(), Collections.emptyList(), user2.getUuid(), place1
                .getUuid()
        );
        이벤트_요청(request3);

        // when
        ExtractableResponse<Response> response = 유저별_포인트_내역_조회(user1.getUuid());
        List<PointHistoryResponse> result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(4);
    }

    public ExtractableResponse<Response> 전체_포인트_내역_조회() {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/point-histories")
                          .then()
                          .log().all()
                          .extract();
    }

    public ExtractableResponse<Response> 유저별_포인트_내역_조회(UUID uuid) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/point-histories/users?id=" + uuid)
                          .then()
                          .log().all()
                          .extract();
    }
}
