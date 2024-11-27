package com.dashtap.DASHTAP.models;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "owners")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Owner extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Vehicle vehicle;

    public Owner(Vehicle vehicle) {
        this.vehicle = vehicle;

    }
}



