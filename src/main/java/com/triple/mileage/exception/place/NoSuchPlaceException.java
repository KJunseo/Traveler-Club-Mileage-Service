package com.triple.mileage.exception.place;

import com.triple.mileage.exception.MileageServiceException;

import org.springframework.http.HttpStatus;

public class NoSuchPlaceException extends MileageServiceException {

    public NoSuchPlaceException() {
        super("존재하지 않는 장소입니다.", HttpStatus.BAD_REQUEST);
    }
}
