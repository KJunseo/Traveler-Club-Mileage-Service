package com.triple.mileage.exception.user;

import com.triple.mileage.exception.MileageServiceException;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends MileageServiceException {

    public NoSuchUserException() {
        super("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
    }
}
