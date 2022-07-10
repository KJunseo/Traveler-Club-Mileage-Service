package com.triple.mileage.common.aop;

import com.triple.mileage.event.domain.EventAction;
import com.triple.mileage.history.domain.PointHistory;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.place.domain.Place;
import com.triple.mileage.review.domain.PointType;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
public class RecordAspect {
    private final PointHistoryRepository pointHistoryRepository;

    public RecordAspect(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Pointcut(value = "@annotation(record) && args(user, place, review)", argNames = "record,user,place,review")
    public void recordPointHistory(Record record, User user, Place place, Review review) {}

    @Around(value = "recordPointHistory(record, user, place, review)", argNames = "joinPoint,record,user,place,review")
    public Object record(ProceedingJoinPoint joinPoint, Record record, User user, Place place, Review review) throws Throwable {
        int prevBasicPoint = review.getBasicPoint();
        int prevBonusPoint = review.getBonusPoint();

        Object result = joinPoint.proceed();

        int curBasicPoint = review.getBasicPoint();
        int bonusPoint = review.getBonusPoint();

        EventAction action = record.action();
        if (EventAction.ADD.equals(action)) {
            record(user, review, PointType.CONTENT, curBasicPoint);
            record(user, review, PointType.BONUS, bonusPoint);
        }
        if (EventAction.MOD.equals(action)) {
            int diff = curBasicPoint - prevBasicPoint;
            record(user, review, PointType.CONTENT, diff);
        }
        if (EventAction.DELETE.equals(action)) {
            record(user, review, PointType.CONTENT, prevBasicPoint * -1);
            record(user, review, PointType.BONUS, prevBonusPoint * -1);
        }

        return result;
    }

    private void record(User user, Review review, PointType type, int point) {
        if (point != 0) {
            PointHistory history = new PointHistory(user, review, type, point);
            pointHistoryRepository.save(history);
        }
    }
}
