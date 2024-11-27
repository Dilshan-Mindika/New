package com.dashtap.DASHTAP.controllers;

import com.dashtap.DASHTAP.payload.requests.AddDriverRentalRequest;
import com.dashtap.DASHTAP.payload.requests.EditDriverRentalRequest;
import com.dashtap.DASHTAP.services.impl.D_RentalServiceImpl;
import com.dashtap.DASHTAP.services.impl.UserServiceImpl;
import com.dashtap.DASHTAP.services.interfac.RentalStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/d_rental")
public class D_RentalController {
    private final D_RentalServiceImpl d_rentalService;
    private final UserServiceImpl userService;
    private final RentalStatusService rentalStatusService;

    public D_RentalController(D_RentalServiceImpl d_rentalService, UserServiceImpl userService, RentalStatusService rentalStatusService) {
        this.d_rentalService = d_rentalService;
        this.userService = userService;
        this.rentalStatusService = rentalStatusService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('OWNER') or hasRole('DRIVER')")
    public ResponseEntity<?> addRental(@RequestBody @Valid AddDriverRentalRequest request){
        if(userService.existsById(request.getUserID())){
            if((request.getEndDate().isAfter(request.getStartDate())) || (request.getEndDate().isEqual(request.getStartDate()))) {
                d_rentalService.add(request);
                return new ResponseEntity<>("Driver rental added", HttpStatus.OK);
            }

            return new ResponseEntity<>("Incorrect date range", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{rentalID}/status/{statusID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<?> changeStatus(@PathVariable("rentalID") Long rentalID, @PathVariable("statusID") Long statusID){
        if(d_rentalService.existsById(rentalID)){
            if(rentalStatusService.existsById(statusID)){
                d_rentalService.changeStatus(statusID, rentalID);
                return new ResponseEntity<>("Driver rental status changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("No driver rental status found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No driver rental found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{rentalID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<?> changeRentalInformation(@PathVariable("rentalID") Long rentalID, @RequestBody @Valid EditDriverRentalRequest request){
        if(d_rentalService.existsById(rentalID)){
            if((request.getEndDate().isAfter(request.getStartDate())) || (request.getEndDate().isEqual(request.getStartDate()))) {
                d_rentalService.update(rentalID, request);
                return new ResponseEntity<>("Driver rent details changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("Incorrect date range", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No driver rental found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{rentalID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<?> deleteRental(@PathVariable("rentalID") Long rentalID){
        if(d_rentalService.existsById(rentalID)){
            d_rentalService.delete(rentalID);
            return new ResponseEntity<>("Driver rent removed successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("No driver rental found", HttpStatus.NOT_FOUND);
    }

}
