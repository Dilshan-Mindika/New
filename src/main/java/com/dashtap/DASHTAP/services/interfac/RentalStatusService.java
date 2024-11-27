package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.models.RentalStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RentalStatusService {

    List<RentalStatus> findAll();

    boolean existsById(Long statusID);

    RentalStatus findByName(RentalStatusEnum rentalStatusEnum);

    Optional<RentalStatus> findById(Long rentalStatus);

}