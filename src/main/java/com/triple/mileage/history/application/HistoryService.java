package com.triple.mileage.history.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.triple.mileage.history.application.dto.PointHistoryResponseDto;
import com.triple.mileage.history.domain.PointHistoryRepository;
import com.triple.mileage.user.application.UserService;
import com.triple.mileage.user.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryService {
    private final PointHistoryRepository pointHistoryRepository;
    private final UserService userService;

    public HistoryService(PointHistoryRepository pointHistoryRepository, UserService userService) {
        this.pointHistoryRepository = pointHistoryRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<PointHistoryResponseDto> findAllPointHistories() {
        return pointHistoryRepository.findAll()
                                     .stream()
                                     .map(history -> new PointHistoryResponseDto(
                                             history.getUserUuid(),
                                             history.getReviewUuid(),
                                             history.getType(),
                                             history.getPoint()))
                                     .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PointHistoryResponseDto> findByUser(UUID id) {
        User user = userService.findByUuid(id);
        return pointHistoryRepository.findAllByUser(user)
                                     .stream()
                                     .map(history -> new PointHistoryResponseDto(
                                             history.getUserUuid(),
                                             history.getReviewUuid(),
                                             history.getType(),
                                             history.getPoint()))
                                     .collect(Collectors.toList());
    }
}
