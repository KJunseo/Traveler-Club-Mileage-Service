package com.triple.mileage.history.domain;

import java.util.List;
import java.util.UUID;

import com.triple.mileage.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, UUID> {
    List<PointHistory> findAllByUser(User user);
}
