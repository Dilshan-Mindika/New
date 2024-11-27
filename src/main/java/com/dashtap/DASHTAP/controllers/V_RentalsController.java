package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.services.impl.V_RentalServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v_rentals")
public class V_RentalsController {
    private final V_RentalServiceImpl v_rentalService;

    public V_RentalsController(V_RentalServiceImpl v_rentalService) {
        this.v_rentalService = v_rentalService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRentals(){
        return ResponseEntity.ok(v_rentalService.findAll());
    }

    @GetMapping("{userID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('DRIVER') or hasRole('OWNER')")
    public ResponseEntity<?> getUserRentals(@PathVariable("userID") Long userID){
        return ResponseEntity.ok(v_rentalService.findByUserId(userID));
    }
}
