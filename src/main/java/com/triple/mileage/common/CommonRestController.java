package com.triple.mileage.common;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewImage;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.user.domain.User;
import com.triple.mileage.user.domain.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 포인트 적립 API를 편하게 실행해 볼 수 있게 하기 위한 임시 컨트롤러입니다.
 */

@RestController
public class CommonRestController {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public CommonRestController(UserRepository userRepository, PlaceRepository placeRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    @GetMapping("/dummy")
    public ResponseEntity<DummyResponse> dummyData() {
        User user = userRepository.findAll().get(0);
        Place place = placeRepository.findAll().get(0);
        Review review = reviewRepository.findAll().get(0);
        List<UUID> photos = review.getReviewImages().stream()
                                  .map(ReviewImage::getUuid)
                                  .collect(Collectors.toList());
        DummyResponse response = new DummyResponse(user.getUuid(), place.getUuid(), review.getUuid(), review.getContent(), photos);
        return ResponseEntity.ok(response);
    }

    static class DummyResponse {
        private UUID userId;
        private UUID placeId;
        private UUID reviewId;
        private String content;
        private List<UUID> attachedPhotoIds;

        public DummyResponse() {
        }

        public DummyResponse(UUID userId, UUID placeId, UUID reviewId, String content, List<UUID> attachedPhotoIds) {
            this.userId = userId;
            this.placeId = placeId;
            this.reviewId = reviewId;
            this.content = content;
            this.attachedPhotoIds = attachedPhotoIds;
        }

        public UUID getUserId() {
            return userId;
        }

        public UUID getPlaceId() {
            return placeId;
        }

        public UUID getReviewId() {
            return reviewId;
        }

        public String getContent() {
            return content;
        }

        public List<UUID> getAttachedPhotoIds() {
            return attachedPhotoIds;
        }
    }
}
