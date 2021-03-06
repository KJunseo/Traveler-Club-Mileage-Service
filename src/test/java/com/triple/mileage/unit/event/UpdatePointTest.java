package com.triple.mileage.unit.event;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.application.eventexecution.UpdatePointEvent;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewPoint;
import com.triple.mileage.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdatePoint 단위 테스트")
public class UpdatePointTest {

    @Test
    @DisplayName("기존 점수 1에서 내용, 사진을 추가하여 3점을 반환한다.")
    void updatePoint() {
        // given
        User user = new User(UUID.randomUUID(), 1);
        Place place = new Place(UUID.randomUUID());
        Review review = new Review(UUID.randomUUID(), "좋아요!", user, place,
                new ReviewPoint(0, 0, 1), List.of(new ReviewImage(UUID.randomUUID()), new ReviewImage(UUID.randomUUID())));

        UpdatePointEvent event = new UpdatePointEvent();

        // when
        event.execute(user, place, review);

        // then
        assertThat(user.getPoint()).isEqualTo(3);
    }

}
