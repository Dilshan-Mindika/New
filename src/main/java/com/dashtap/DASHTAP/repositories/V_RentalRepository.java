package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.V_Rental;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface V_RentalRepository extends JpaRepository<V_Rental, Long> {

    @Query("select (count(r) > 0) from V_Rental r where (r.startDate >= ?1 and r.endDate <= ?1) and r.rentalStatus.name = ?2")
    boolean existsByRentalDateAndRentalStatus(LocalDate startDate, RentalStatusEnum name);

    boolean existsByVehicleId(Long id);

}
