package com.triple.mileage.place.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByUuid(UUID uuid);
}
