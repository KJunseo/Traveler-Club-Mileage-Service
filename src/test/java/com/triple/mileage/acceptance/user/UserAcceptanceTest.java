package com.triple.mileage.acceptance.user;

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
import com.triple.mileage.user.presentation.dto.UserPointResponse;

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

@DisplayName("User 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("유저별 포인트 조회")
    void showUserPoint() {
        // given
        // TODO user, place, review 생성 api가 만들어진다면 repository 호출대신 해당 api 호출로 대체
        User user = userRepository.save(new User(0));
        Place place = placeRepository.save(new Place());
        Review review = reviewRepository.save(new Review("좋아요!", user, place, List.of(new ReviewImage(), new ReviewImage())));
        List<UUID> photos = review.getReviewImages().stream().map(ReviewImage::getId).collect(Collectors.toList());

        EventRequest request = new EventRequest(
                "REVIEW", "ADD", review.getId(), review.getContent(), photos, user.getId(), place.getId()
        );
        이벤트_요청(request);


        // when
        ExtractableResponse<Response> response = 유저별_포인트_조회(user.getId());
        UserPointResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getPoint()).isEqualTo(3);
    }

    private ExtractableResponse<Response> 유저별_포인트_조회(UUID uuid) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/my-points?id=" + uuid)
                          .then()
                          .log().all()
                          .extract();
    }

}
