package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.D_Rental;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface D_RentalRepository extends JpaRepository<D_Rental, Long> {

    @Query("select (count(r) > 0) from D_Rental r where (r.startDate >= ?1 and r.endDate <= ?1) and r.rentalStatus.name = ?2")
    boolean existsByRentalDateAndRentalStatus(LocalDate startDate, RentalStatusEnum name);

    boolean existsByDriverId(Long id);

}
