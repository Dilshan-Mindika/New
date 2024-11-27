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
public class D_RentalResponse {
    private Long id;
    private String username;
    private Long price;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate addDate;
    private Integer driverPrice;
    private RentalStatus rentalStatus;
    private List<StatusHistory> statusHistory = new ArrayList<>();

    public D_RentalResponse(String username, Long id, Long price, LocalDate startDate, LocalDate endDate, LocalDate addDate,
                            Integer driverPrice, RentalStatus rentalStatus, List<StatusHistory> statusHistory) {
        this.username = username;
        this.id = id;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.addDate = addDate;
        this.driverPrice = driverPrice;
        this.rentalStatus = rentalStatus;
        this.statusHistory = statusHistory;
    }
}
