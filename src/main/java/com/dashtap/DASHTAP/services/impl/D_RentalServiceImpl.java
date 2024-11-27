package com.dashtap.DASHTAP.services.impl;

import com.dashtap.DASHTAP.models.*;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.payload.requests.AddDriverRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditDriverRentalRequest;
import com.dashtap.DASHTAP.payload.responses.D_RentalResponse;
import com.dashtap.DASHTAP.repositories.*;
import com.dashtap.DASHTAP.services.interfac.D_RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("driverRentalService")
@RequiredArgsConstructor
public class D_RentalServiceImpl implements D_RentalService {
    private final D_RentalRepository d_rentalRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RentalStatusRepository rentalStatusRepository;

    @Override
    public void changeStatus(Long statusID, Long d_rentalID) {
        D_Rental d_rental = d_rentalRepository.getReferenceById(d_rentalID);
        RentalStatus rentalStatus = rentalStatusRepository.findById(statusID).orElseThrow(() -> new RuntimeException("Error: Rental status is not found"));
        d_rental.setRentalStatus(rentalStatus);

        StatusHistory newStatus = new StatusHistory(rentalStatus, LocalDate.now());
        d_rental.getStatusHistory().add(addHistory(newStatus));
        d_rentalRepository.save(d_rental);
    }

    @Override
    public List<D_RentalResponse> findAll() {
        return userRepository.findAll().stream()
                .flatMap(user -> user.getD_Rentals().stream()
                        .map(rental -> new D_RentalResponse(
                                user.getUsername(),
                                rental.getId(),
                                rental.getPrice(),
                                rental.getStartDate(),
                                rental.getEndDate(),
                                rental.getAddDate(),
                                rental.getDriver().getPrice(),
                                rental.getRentalStatus(),
                                rental.getStatusHistory()
                        )))
                .collect(Collectors.toList());
    }

    @Override
    public List<D_RentalResponse> findByUserId(Long userID) {
        User user = userRepository.getReferenceById(userID);

        return user.getD_Rentals().stream()
                .map(rental -> new D_RentalResponse(
                        user.getUsername(),
                        rental.getId(),
                        rental.getPrice(),
                        rental.getStartDate(),
                        rental.getEndDate(),
                        rental.getAddDate(),
                        rental.getDriver().getPrice(),
                        rental.getRentalStatus(),
                        rental.getStatusHistory()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long d_rentalID) {
        return d_rentalRepository.existsById(d_rentalID);
    }

    @Override
    public void update(Long d_rentalID, EditDriverRentalRequest request) {
        D_Rental d_rental = d_rentalRepository.getReferenceById(d_rentalID);
        d_rental.setPrice((ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1) * d_rental.getDriver().getPrice());
        d_rental.setStartDate(request.getStartDate());
        d_rental.setEndDate(request.getEndDate());
        d_rentalRepository.save(d_rental);
    }

    @Override
    @Transactional
    public void delete(Long d_rentalID) {
        d_rentalRepository.deleteById(d_rentalID);
    }

    @Override
    public D_Rental findById(Long d_rentalID) {
        return d_rentalRepository.getReferenceById(d_rentalID);
    }

    @Override
    public void add(AddDriverRentalRequest request) {
        Driver driver = driverRepository.getReferenceById(request.getDriverID());
        StatusHistory statusHistory = new StatusHistory(rentalStatusRepository.findByName(RentalStatusEnum.STATUS_PENDING), request.getAddDate());

        User user = userRepository.getReferenceById(request.getUserID());
        D_Rental dRental = new D_Rental(
                driver,
                request.getStartDate(),
                request.getEndDate(),
                request.getAddDate(),
                (ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1) * driver.getPrice(),
                rentalStatusRepository.findByName(RentalStatusEnum.STATUS_PENDING),
                Collections.singletonList(addHistory(statusHistory))
        );

        user.getD_Rentals().add(d_rentalRepository.save(dRental));
        userRepository.save(user);
    }

    @Override
    public boolean existsDateAndStatus(LocalDate date, RentalStatusEnum status) {
        return d_rentalRepository.existsByRentalDateAndRentalStatus(date, status);
    }

    @Override
    public boolean existsByDriverId(Long id) {
        return d_rentalRepository.existsByDriverId(id);
    }

    private StatusHistory addHistory(StatusHistory status) {
        return statusHistoryRepository.save(status);
    }
}
