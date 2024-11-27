package com.dashtap.DASHTAP.payload.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddVehicleRentalRequest {

    @NotNull
    private Long vehicleID;

    @NotNull
    private Long userID;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate addDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;

}
