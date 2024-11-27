package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.payload.requests.AddVehicleRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRentalRequest;
import com.dashtap.DASHTAP.services.impl.V_RentalServiceImpl;
import com.dashtap.DASHTAP.services.impl.VehicleServiceImpl;
import com.dashtap.DASHTAP.services.interfac.RentalStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v_rental")
public class V_RentalController {
    private final V_RentalServiceImpl v_rentalService;
    private final VehicleServiceImpl vehicleService;
    private final RentalStatusService rentalStatusService;

    public V_RentalController(V_RentalServiceImpl v_rentalService, VehicleServiceImpl vehicleService, RentalStatusService rentalStatusService) {
        this.v_rentalService = v_rentalService;
        this.vehicleService = vehicleService;
        this.rentalStatusService = rentalStatusService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('OWNER') or hasRole('DRIVER')")
    public ResponseEntity<?> addRental(@RequestBody @Valid AddVehicleRentalRequest request){
        if(vehicleService.existsById(request.getVehicleID())){
            if((request.getEndDate().isAfter(request.getStartDate())) || (request.getEndDate().isEqual(request.getStartDate()))) {
                v_rentalService.add(request);
                return new ResponseEntity<>("Vehicle rental added", HttpStatus.OK);
            }

            return new ResponseEntity<>("Incorrect date range", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{rentalID}/status/{statusID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> changeStatus(@PathVariable("rentalID") Long rentalID, @PathVariable("statusID") Long statusID){
        if(v_rentalService.existsById(rentalID)){
            if(rentalStatusService.existsById(statusID)){
                v_rentalService.changeStatus(statusID, rentalID);
                return new ResponseEntity<>("Vehicle rental status changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("No vehicle rental status found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No vehicle rental found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{rentalID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> changeRentalInformation(@PathVariable("rentalID") Long rentalID, @RequestBody @Valid EditVehicleRentalRequest request){
        if(v_rentalService.existsById(rentalID)){
            if((request.getEndDate().isAfter(request.getStartDate())) || (request.getEndDate().isEqual(request.getStartDate()))) {
                v_rentalService.update(rentalID, request);
                return new ResponseEntity<>("Vehicle rent details changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("Incorrect date range", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No vehicle rental found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{rentalID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> deleteRental(@PathVariable("rentalID") Long rentalID){
        if(v_rentalService.existsById(rentalID)){
            v_rentalService.delete(rentalID);
            return new ResponseEntity<>("Vehicle rent removed successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("No vehicle rental found", HttpStatus.NOT_FOUND);
    }

}
