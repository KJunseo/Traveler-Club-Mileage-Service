package com.triple.mileage.unit.event;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.application.eventexecution.IncreasePointEvent;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IncreasePoint 단위 테스트")
public class IncreasePointTest {

    @Test
    @DisplayName("내용과 사진을 포함하여 첫번째로 리뷰를 작성하여 3포인트를 얻는다.")
    void increasePoint() {
        // given
        User user = new User(UUID.randomUUID(), 0);
        UUID placeId = UUID.randomUUID();
        Place place = new Place(placeId);
        Review review = new Review(UUID.randomUUID(), "좋아요!", user, place, List.of(new ReviewImage(), new ReviewImage()));
        place = new Place(placeId, List.of(review));

        IncreasePointEvent event = new IncreasePointEvent();

        // when
        event.execute(user, place, review);

        // then
        assertThat(user.getPoint()).isEqualTo(3);
    }

}
