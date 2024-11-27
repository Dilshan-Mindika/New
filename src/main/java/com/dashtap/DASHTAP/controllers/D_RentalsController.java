package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.services.impl.D_RentalServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/d_rentals")
public class D_RentalsController {
    private final D_RentalServiceImpl d_rentalService;

    public D_RentalsController(D_RentalServiceImpl d_rentalService) {
        this.d_rentalService = d_rentalService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRentals(){
        return ResponseEntity.ok(d_rentalService.findAll());
    }

    @GetMapping("{userID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('DRIVER') or hasRole('OWNER')")
    public ResponseEntity<?> getUserRentals(@PathVariable("userID") Long userID){
        return ResponseEntity.ok(d_rentalService.findByUserId(userID));
    }
}
