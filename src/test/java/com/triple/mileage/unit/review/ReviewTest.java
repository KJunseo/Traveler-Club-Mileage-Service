package com.triple.mileage.unit.review;

import java.util.Collections;
import java.util.List;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Review 도메인 단위 테스트")
class ReviewTest {

    @Test
    @DisplayName("Review 내용이 비어있고 사진이 없다면 0점을 반환한다.")
    void empty() {
        // given
        Review review = new Review("", new User(0), new Place(), Collections.emptyList());

        // when
        int point = review.getPoint();

        // then
        assertThat(point).isEqualTo(0);
    }

    @Test
    @DisplayName("Review 내용이 1자 이상이면 1점을 얻는다.")
    void content() {
        // given
        Review review = new Review("굿", new User(0), new Place(), Collections.emptyList());

        // when
        int point = review.getPoint();

        // then
        assertThat(point).isEqualTo(1);
    }


    @Test
    @DisplayName("Review 사진이이 1장 이상이면 1점을 얻는다.")
    void photo() {
        // given
        Review review = new Review("", new User(0), new Place(), List.of(new ReviewImage()));

        // when
        int point = review.getPoint();

        // then
        assertThat(point).isEqualTo(1);
    }

    @Test
    @DisplayName("Review 내용이 1자 이상, 사진이이 1장 이상이면 2점을 얻는다.")
    void contentAndPhoto() {
        // given
        Review review = new Review("굿", new User(0), new Place(), List.of(new ReviewImage()));

        // when
        int point = review.getPoint();

        // then
        assertThat(point).isEqualTo(2);
    }
}
