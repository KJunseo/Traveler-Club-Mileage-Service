package com.triple.mileage.user.application;

import java.util.UUID;

import com.triple.mileage.exception.user.NoSuchUserException;
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
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                             .orElseThrow(NoSuchUserException::new);
    }
}
