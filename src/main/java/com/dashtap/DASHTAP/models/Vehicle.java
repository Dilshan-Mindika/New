package com.dashtap.DASHTAP.models;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.nio.file.Path;

@Entity
@Table(name = "vehicles",
    uniqueConstraints = {
    @UniqueConstraint(columnNames = "vehicle_number")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private VehicleModel vehicleModel;

    @NotBlank
    private String vehicleNumber;

    @NotNull
    @Min(3)
    private String color;

    @NotNull
    @Min(10)
    private String description;

    @NotNull
    @Min(1970)
    private Integer year;

    @NotNull
    @Min(1)
    private Integer mileage;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @NotNull
    @Min(50)
    private Integer horsePower;

    @NotBlank
    private String capacity;

    @NotNull
    @Min(50)
    private Integer price;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "vehicle_image_image_id")
    private VehicleImage vehicleImage;

    public Vehicle(Brand brand, VehicleModel vehicleModel, String color, Integer year, Integer mileage, FuelType fuelType, Integer horsePower, String capacity, String description, String vehicleNumber, Integer price, boolean available, VehicleImage vehicleImage) {
        this.brand = brand;
        this.vehicleModel = vehicleModel;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.vehicleNumber = vehicleNumber;
        this.horsePower = horsePower;
        this.capacity = capacity;
        this.price = price;
        this.available = available;
        this.vehicleImage = vehicleImage;
        this.description = description;
    }
}

