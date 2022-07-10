package com.triple.mileage.history.presentation;

import java.util.List;
import java.util.stream.Collectors;

import com.triple.mileage.history.application.HistoryService;
import com.triple.mileage.history.application.dto.PointHistoryResponseDto;
import com.triple.mileage.history.presentation.dto.PointHistoryResponse;
import com.triple.mileage.user.presentation.dto.UserPointRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryRestController {
    private final HistoryService historyService;

    public HistoryRestController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/point-histories")
    public ResponseEntity<List<PointHistoryResponse>> allHistories() {
        List<PointHistoryResponseDto> responseDto = historyService.findAllPointHistories();
        List<PointHistoryResponse> responses = responseDto.stream()
                                                          .map(PointHistoryResponse::new)
                                                          .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/point-histories/users")
    public ResponseEntity<List<PointHistoryResponse>> userHistories(UserPointRequest request) {
        List<PointHistoryResponseDto> responseDto = historyService.findByUser(request.getId());
        List<PointHistoryResponse> responses = responseDto.stream()
                                                          .map(PointHistoryResponse::new)
                                                          .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
