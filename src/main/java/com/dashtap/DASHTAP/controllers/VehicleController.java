package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.models.Vehicle;
import com.dashtap.DASHTAP.models.Enums.RentalStatusEnum;
import com.dashtap.DASHTAP.payload.requests.AddVehicleRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRequest;
import com.dashtap.DASHTAP.services.impl.V_RentalServiceImpl;
import com.dashtap.DASHTAP.services.impl.VehicleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/car")
public class VehicleController {
    private final V_RentalServiceImpl v_rentalService;
    private final VehicleServiceImpl vehicleService;

    public VehicleController(V_RentalServiceImpl v_rentalService, VehicleServiceImpl vehicleService) {
        this.v_rentalService = v_rentalService;
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> addVehicle(@RequestBody @Valid AddVehicleRequest vehicleRequest){
        vehicleService.add(vehicleRequest);
        return ResponseEntity.ok(vehicleService.findAvailableVehicles());
    }

    @Transactional
    @PutMapping("{carID}/image")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> changeVehicleImage(@PathVariable("vehicleID") Long vehicleID, @RequestBody MultipartFile file) throws IOException {
        if(vehicleService.existsById(vehicleID)){
            vehicleService.changeImage(vehicleID, file);
            return new ResponseEntity<>("Vehicle photo changed", HttpStatus.OK);
        }

        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{carID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> editVehicle(@PathVariable("vehicleID") Long vehicleID, @RequestBody @Valid EditVehicleRequest vehicleRequest){
        if(vehicleService.existsById(vehicleID)){
            vehicleService.update(vehicleID, vehicleRequest);
            return new ResponseEntity<>("Vehicle information changed", HttpStatus.OK);
        }

        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("{carID}")
    public ResponseEntity<?> getVehicle(@PathVariable("vehicleID") Long vehicleID){
        return ResponseEntity.ok(vehicleService.getVehicleById(vehicleID));
    }

    @PutMapping("{carID}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> changeVehicleStatus(@PathVariable("vehicleID") Long vehicleID){
        if(vehicleService.existsById(vehicleID)){
            Vehicle vehicle = vehicleService.getVehicleById(vehicleID);

            if(!v_rentalService.existsDateAndStatus(LocalDate.now(), RentalStatusEnum.STATUS_ACCEPTED)){
                vehicleService.changeStatus(vehicle);
                return new ResponseEntity<>("The availability of the vehicle has been changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("Vehicle has active rental", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{carID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> deleteVehicle(@PathVariable("vehicleID") Long vehicleID){
        if(vehicleService.existsById(vehicleID)){
            Vehicle vehicle = vehicleService.getVehicleById(vehicleID);

            if(!v_rentalService.existsByVehicleId(vehicle.getId())){
                vehicleService.delete(vehicle);
                return new ResponseEntity<>("Vehicle removed successfully", HttpStatus.OK);
            }

            return new ResponseEntity<>("You cannot remove a vehicle if it has a rental assigned to it", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

}
