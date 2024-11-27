package com.dashtap.DASHTAP.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle_Images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageID;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB", nullable = false)
    private byte[] fileContent;

    public VehicleImage(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}
