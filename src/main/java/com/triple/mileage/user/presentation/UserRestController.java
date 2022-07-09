package com.triple.mileage.user.presentation;

import com.triple.mileage.user.application.UserService;
import com.triple.mileage.user.application.dto.UserPointRequestDto;
import com.triple.mileage.user.application.dto.UserPointResponseDto;
import com.triple.mileage.user.presentation.dto.UserPointRequest;
import com.triple.mileage.user.presentation.dto.UserPointResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-points")
    public ResponseEntity<UserPointResponse> showUserPoint(UserPointRequest request) {
        UserPointRequestDto requestDto = new UserPointRequestDto(request.getId());
        UserPointResponseDto responseDto = userService.findPoints(requestDto);
        UserPointResponse response = new UserPointResponse(responseDto);
        return ResponseEntity.ok(response);
    }
}
