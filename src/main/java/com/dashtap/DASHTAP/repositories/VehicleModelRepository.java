package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {

    VehicleModel findByName(String name);

    void deleteByName(String name);

}
