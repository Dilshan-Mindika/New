package com.dashtap.DASHTAP.services.impl;

import com.dashtap.DASHTAP.models.*;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.payload.requests.AddVehicleRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRentalRequest;
import com.dashtap.DASHTAP.payload.responses.V_RentalResponse;
import com.dashtap.DASHTAP.repositories.*;
import com.dashtap.DASHTAP.services.interfac.V_RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("vehicleRentalService")
@RequiredArgsConstructor
public class V_RentalServiceImpl implements V_RentalService {
    private final V_RentalRepository v_rentalRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RentalStatusRepository rentalStatusRepository;

    @Override
    public void changeStatus(Long statusID, Long v_rentalID) {
        V_Rental v_rental = v_rentalRepository.getReferenceById(v_rentalID);
        RentalStatus rentalStatus = rentalStatusRepository.findById(statusID).orElseThrow(() -> new RuntimeException("Error: Rental status is not found"));
        v_rental.setRentalStatus(rentalStatus);

        StatusHistory newStatus = new StatusHistory(rentalStatus, LocalDate.now());
        v_rental.getStatusHistory().add(addHistory(newStatus));
        v_rentalRepository.save(v_rental);
    }

    @Override
    public List<V_RentalResponse> findAll() {
        return userRepository.findAll().stream()
                .flatMap(user -> user.getV_Rentals().stream()
                        .map(rental -> new V_RentalResponse(
                                user.getUsername(),
                                rental.getId(),
                                rental.getVehicle().getPrice(),
                                rental.getVehicle().getBrand().getName(),
                                rental.getVehicle().getVehicleModel().getName(),
                                rental.getStartDate(),
                                rental.getEndDate(),
                                rental.getAddDate(),
                                rental.getPrice(),
                                rental.getRentalStatus(),
                                rental.getStatusHistory().stream().toList()
                        )))
                .collect(Collectors.toList());
    }

    @Override
    public List<V_RentalResponse> findByUserId(Long userID) {
        User user = userRepository.getReferenceById(userID);

        return user.getV_Rentals().stream()
                .map(rental -> new V_RentalResponse(
                        user.getUsername(),
                        rental.getId(),
                        rental.getVehicle().getPrice(),
                        rental.getVehicle().getBrand().getName(),
                        rental.getVehicle().getVehicleModel().getName(),
                        rental.getStartDate(),
                        rental.getEndDate(),
                        rental.getAddDate(),
                        rental.getPrice(),
                        rental.getRentalStatus(),
                        rental.getStatusHistory().stream().toList()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long v_rentalID) {
        return v_rentalRepository.existsById(v_rentalID);
    }

    @Override
    public void update(Long v_rentalID, EditVehicleRentalRequest request) {
        V_Rental v_rental = v_rentalRepository.getReferenceById(v_rentalID);
        v_rental.setPrice((ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate())+1) * v_rental.getVehicle().getPrice());
        v_rental.setStartDate(request.getStartDate());
        v_rental.setEndDate(request.getEndDate());
        v_rentalRepository.save(v_rental);
    }

    @Override
    @Transactional
    public void delete(Long v_rentalID) {
        v_rentalRepository.deleteById(v_rentalID);
    }

    @Override
    public V_Rental findById(Long v_rentalID) {
        return v_rentalRepository.getReferenceById(v_rentalID);
    }

    @Override
    public void add(AddVehicleRentalRequest request) {
        Vehicle vehicle = vehicleRepository.getReferenceById(request.getVehicleID());
        StatusHistory statusHistory = new StatusHistory(rentalStatusRepository.findByName(RentalStatusEnum.STATUS_PENDING), request.getAddDate());

        User user = userRepository.getReferenceById(request.getUserID());
        user.getV_Rentals().add(v_rentalRepository.save(
                        new V_Rental(
                                vehicle,
                                request.getStartDate(),
                                request.getEndDate(),
                                request.getAddDate(),
                                (ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate())+1) * vehicle.getPrice(),
                                rentalStatusRepository.findByName(RentalStatusEnum.STATUS_PENDING),
                                Collections.singletonList(addHistory(statusHistory))
                        )
                )
        );
        userRepository.save(user);
    }

    @Override
    public boolean existsDateAndStatus(LocalDate date, RentalStatusEnum status) {
        return v_rentalRepository.existsByRentalDateAndRentalStatus(date, status);
    }

    @Override
    public boolean existsByVehicleId(Long id) {
        return v_rentalRepository.existsByVehicleId(id);
    }

    private StatusHistory addHistory(StatusHistory status) {
        return statusHistoryRepository.save(status);
    }
}
