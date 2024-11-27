package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.services.impl.VehicleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vehicles")
public class VehiclesController {
    private final VehicleServiceImpl vehicleService;

    public VehiclesController(VehicleServiceImpl vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("available")
    public ResponseEntity<?> getAvailableVehicles(){
        return ResponseEntity.ok(vehicleService.findAvailableVehicles());
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles(){
        return ResponseEntity.ok(vehicleService.findAll());
    }
}
