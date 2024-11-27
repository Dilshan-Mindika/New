package com.dashtap.DASHTAP.services.impl;

import com.dashtap.DASHTAP.models.FuelType;
import com.dashtap.DASHTAP.repositories.FuelTypeRepository;
import com.dashtap.DASHTAP.services.interfac.FuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("fuelService")
@RequiredArgsConstructor
public class FuelServiceImpl implements FuelService {
    private final FuelTypeRepository fuelTypeRepository;

    @Override
    public List<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }

    @Override
    public Optional<FuelType> findById(Long fuelType) {
        return fuelTypeRepository.findById(fuelType);
    }
}
