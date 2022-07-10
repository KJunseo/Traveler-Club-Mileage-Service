package com.triple.mileage.user.application;

import java.util.UUID;

import com.triple.mileage.exception.user.NoSuchUserException;
import com.triple.mileage.user.application.dto.UserPointRequestDto;
import com.triple.mileage.user.application.dto.UserPointResponseDto;
import com.triple.mileage.user.domain.User;
import com.triple.mileage.user.domain.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByUuid(UUID userId) {
        return userRepository.findByUuid(userId)
                             .orElseThrow(NoSuchUserException::new);
    }

    @Transactional(readOnly = true)
    public UserPointResponseDto findPoints(UserPointRequestDto requestDto) {
        User user = findByUuid(requestDto.getId());
        return new UserPointResponseDto(user.getPoint());
    }
}
