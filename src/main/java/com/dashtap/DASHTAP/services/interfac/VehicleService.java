package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.Vehicle;
import com.dashtap.DASHTAP.payload.requests.AddVehicleRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface VehicleService {
    List<Vehicle> findAvailableVehicles();

    List<Vehicle> findAll();

    boolean existsById(Long vehicleID);

    Vehicle getVehicleById(Long vehicleID);

    void add(AddVehicleRequest vehicleRequest);

    void changeImage(Long vehicleID, MultipartFile file) throws IOException;

    void delete(Vehicle vehicle);

    void changeStatus(Vehicle vehicle);

    void update(Long vehicleID, EditVehicleRequest vehicleRequest);
}

