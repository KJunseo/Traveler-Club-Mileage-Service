package com.triple.mileage.place.application;

import java.util.UUID;

import com.triple.mileage.place.domain.Place;
import com.triple.mileage.place.domain.PlaceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional(readOnly = true)
    public Place findById(UUID placeId) {
        return placeRepository.findById(placeId)
                              .orElseThrow();
    }
}
