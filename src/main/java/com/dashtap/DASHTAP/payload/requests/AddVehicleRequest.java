package com.dashtap.DASHTAP.payload.requests;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddVehicleRequest {

    @NotNull
    private Integer horsePower;

    @NotNull
    private Integer price;

    @NotNull
    private String color;

    @NotBlank
    private String description;

    @NotBlank
    private String vehicleNumber;

    @NotNull
    private Integer year;

    @NotNull
    private Integer mileage;

    @NotBlank
    private String brand;

    @NotBlank
    private String vehicleModel;

    @NotBlank
    private String capacity;

    @NotNull
    private Long fuelType;

}
