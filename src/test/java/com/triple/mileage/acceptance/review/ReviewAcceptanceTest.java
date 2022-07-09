package com.triple.mileage.acceptance.review;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.triple.mileage.acceptance.AcceptanceTest;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.presentation.dto.ReviewRequest;
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

@DisplayName("Review 인수테스트")
public class ReviewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 수정 API")
    void updatePoint() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(0));
        Place place = placeRepository.save(new Place());
        Review review = reviewRepository.save(new Review("좋아요!", user, place, List.of(new ReviewImage(), new ReviewImage())));

        ReviewRequest reviewRequest = new ReviewRequest("너무 좋았습니다!", Collections.emptyList());

        // when
        ExtractableResponse<Response> response = 리뷰_수정(review.getId(), reviewRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 리뷰_수정(UUID id, ReviewRequest request) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .patch("/reviews/" + id)
                          .then()
                          .log().all()
                          .extract();
    }
}
