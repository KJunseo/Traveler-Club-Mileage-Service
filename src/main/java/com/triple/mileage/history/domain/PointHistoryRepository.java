package com.triple.mileage.history.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, UUID> {
}
