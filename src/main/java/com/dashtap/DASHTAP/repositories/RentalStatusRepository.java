package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.models.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalStatusRepository extends JpaRepository<RentalStatus, Long> {

    RentalStatus findByName(RentalStatusEnum name);

}
