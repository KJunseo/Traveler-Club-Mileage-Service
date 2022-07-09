package com.triple.mileage.exception;

import org.springframework.http.HttpStatus;

public class MileageServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MileageServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
