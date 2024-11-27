package com.dashtap.DASHTAP.models;

import com.dashtap.DASHTAP.models.Enums.FuelTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fuel_types")
@NoArgsConstructor
@Getter
@Setter
public class FuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private FuelTypeEnum name;

    public FuelType(FuelTypeEnum name){
        this.name = name;
    }
}