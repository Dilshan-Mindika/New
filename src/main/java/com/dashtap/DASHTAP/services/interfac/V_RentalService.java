package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.models.V_Rental;
import com.dashtap.DASHTAP.payload.requests.AddVehicleRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRentalRequest;
import com.dashtap.DASHTAP.payload.responses.V_RentalResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface V_RentalService {
    void changeStatus(Long statusID, Long v_rentalID);

    List<V_RentalResponse> findAll();

    List<V_RentalResponse> findByUserId(Long userID);

    boolean existsById(Long v_rentalID);

    void update(Long v_rentalID, EditVehicleRentalRequest request);

    void delete(Long v_rentalID);

    V_Rental findById(Long v_rentalID);

    void add(AddVehicleRentalRequest request);

    boolean existsDateAndStatus(LocalDate now, RentalStatusEnum rentalStatusEnum);

    boolean existsByVehicleId(Long id);

}

