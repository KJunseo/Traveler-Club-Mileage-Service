package com.triple.mileage.exception;

import com.triple.mileage.exception.dto.ExceptionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MileageServiceException.class)
    public ResponseEntity<ExceptionResponse> applicationException(MileageServiceException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(exceptionResponse);
    }
}
