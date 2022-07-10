package com.triple.mileage.exception.review;

import com.triple.mileage.exception.MileageServiceException;

import org.springframework.http.HttpStatus;

public class NoSuchReviewException extends MileageServiceException {

    public NoSuchReviewException() {
        super("존재하지 않는 리뷰입니다.", HttpStatus.BAD_REQUEST);
    }
}
