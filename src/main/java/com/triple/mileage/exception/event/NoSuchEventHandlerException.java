package com.triple.mileage.exception.event;

import com.triple.mileage.exception.MileageServiceException;

import org.springframework.http.HttpStatus;

public class NoSuchEventHandlerException extends MileageServiceException {

    public NoSuchEventHandlerException() {
        super("처리할 수 없는 이벤트 요청입니다. 알맞은 type과 action을 입력해주세요", HttpStatus.BAD_REQUEST);
    }
}
