package com.triple.mileage.unit.event;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.application.eventexecution.DecreasePointEvent;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewPoint;
import com.triple.mileage.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DecreasePoint 단위 테스트")
public class DecreasePointTest {

    @Test
    @DisplayName("리뷰를 삭제해서 점수가 초기화 된다.")
    void updatePoint() {
        // given
        User user = new User(UUID.randomUUID(), 7);
        Place place = new Place(UUID.randomUUID());
        Review review = new Review(UUID.randomUUID(), "좋아요!", user, place,
                new ReviewPoint(1, 1, 1), List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));

        DecreasePointEvent event = new DecreasePointEvent();

        // when
        event.execute(user, place, review);

        // then
        assertThat(user.getPoint()).isEqualTo(4);
    }

}
