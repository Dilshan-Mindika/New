package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.FuelType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FuelService {
    List<FuelType> findAll();

    Optional<FuelType> findById(Long fuelType);
}
