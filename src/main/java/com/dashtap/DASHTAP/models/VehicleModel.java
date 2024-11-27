package com.dashtap.DASHTAP.models;

import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "vehicle_Models")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;

    public VehicleModel(String name){
        this.name = name;
    }
}