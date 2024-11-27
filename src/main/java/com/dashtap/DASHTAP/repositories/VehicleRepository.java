package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAvailable(boolean available);

    Vehicle getVehicleById(Long vehicleID);

    long countByModelName(String name);

    long countByBrandName(String name);

    boolean existsByBrandName(String name);

    boolean existsByModelName(String name);

}
