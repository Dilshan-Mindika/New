package com.dashtap.DASHTAP.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "license_Images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LicenseImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageID;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB", nullable = false)
    private byte[] fileContent;

    public LicenseImage(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}
