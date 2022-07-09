package com.triple.mileage.unit.event;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.event.application.eventexecution.DecreasePointEvent;
import com.triple.mileage.history.domain.PointHistory;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.user.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("DecreasePoint 단위 테스트")
@ExtendWith(MockitoExtension.class)
public class DecreasePointTest {

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @Test
    @DisplayName("리뷰를 삭제해서 점수가 초기화 된다.")
    void updatePoint() {
        // given
        User user = new User(UUID.randomUUID(), 3);
        Place place = new Place(UUID.randomUUID());
        Review review = new Review(UUID.randomUUID(), "좋아요!", user, place, List.of(new ReviewImage(), new ReviewImage()));
        PointHistory history = new PointHistory(user, review, PointType.BONUS, 1);

        given(pointHistoryRepository.findAllByUserAndReview(user, review))
                .willReturn(List.of(history));

        DecreasePointEvent event = new DecreasePointEvent(pointHistoryRepository);

        // when
        event.execute(user, place, review);

        // then
        assertThat(user.getPoint()).isEqualTo(0);
    }

}
