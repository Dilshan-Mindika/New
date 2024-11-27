package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.D_Rental;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.payload.requests.AddDriverRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditDriverRentalRequest;
import com.dashtap.DASHTAP.payload.responses.D_RentalResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface D_RentalService {
    void changeStatus(Long statusID, Long d_rentalID);

    List<D_RentalResponse> findAll();

    List<D_RentalResponse> findByUserId(Long userID);

    boolean existsById(Long d_rentalID);

    void update(Long d_rentalID, EditDriverRentalRequest request);

    void delete(Long d_rentalID);

    D_Rental findById(Long d_rentalID);

    void add(AddDriverRentalRequest request);

    boolean existsDateAndStatus(LocalDate now, RentalStatusEnum rentalStatusEnum);

    boolean existsByDriverId(Long id);

}

