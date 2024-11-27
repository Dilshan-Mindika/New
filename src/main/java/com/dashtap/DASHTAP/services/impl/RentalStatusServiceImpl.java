package com.dashtap.DASHTAP.services.impl;

import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.models.RentalStatus;
import com.dashtap.DASHTAP.repositories.RentalStatusRepository;
import com.dashtap.DASHTAP.services.interfac.RentalStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("rentalStatusService")
@RequiredArgsConstructor
public class RentalStatusServiceImpl implements RentalStatusService {
    private final RentalStatusRepository rentalStatusRepository;

    @Override
    public List<RentalStatus> findAll() {
        return rentalStatusRepository.findAll();
    }

    @Override
    public boolean existsById(Long statusID) {
        return rentalStatusRepository.existsById(statusID);
    }

    @Override
    public RentalStatus findByName(RentalStatusEnum rentalStatusEnum) {
        return rentalStatusRepository.findByName(rentalStatusEnum);
    }

    @Override
    public Optional<RentalStatus> findById(Long rentalStatus) {
        return rentalStatusRepository.findById(rentalStatus);
    }
}

