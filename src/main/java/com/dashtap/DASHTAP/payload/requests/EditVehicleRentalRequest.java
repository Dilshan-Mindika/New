package com.dashtap.DASHTAP.payload.requests;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditVehicleRentalRequest {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
