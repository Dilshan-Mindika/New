package com.dashtap.DASHTAP.payload.responses;

import com.dashtap.DASHTAP.models.RentalStatus;
import com.dashtap.DASHTAP.models.StatusHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class V_RentalResponse {

    private Long id;
    private String username;
    private Integer vehiclePrice;
    private String vehicleBrand;
    private String vehicleModel;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate addDate;
    private Long price;
    private RentalStatus rentalStatus;
    private List<StatusHistory> statusHistory = new ArrayList<>();

    public V_RentalResponse(String username, Long id, Integer vehiclePrice, String vehicleBrand, String vehicleModel,
                            LocalDate startDate, LocalDate endDate, LocalDate addDate, Long price,
                            RentalStatus rentalStatus, List<StatusHistory> statusHistory) {
        this.username = username;
        this.id = id;
        this.vehiclePrice = vehiclePrice;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.addDate = addDate;
        this.price = price;
        this.rentalStatus = rentalStatus;
        this.statusHistory = statusHistory;
    }
}
