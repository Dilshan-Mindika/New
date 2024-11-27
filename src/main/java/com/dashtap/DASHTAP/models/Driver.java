package com.dashtap.DASHTAP.models;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Driver extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(50)
    private Integer price;

    @NotBlank
    private Integer drivingLicenseNumber;

    @NotBlank
    private LicenseImage licenseImage;

    public Driver(Integer drivingLicenseNumber, LicenseImage licenseImage, Integer price) {
        this.price = price;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.licenseImage = licenseImage;
    }
}


