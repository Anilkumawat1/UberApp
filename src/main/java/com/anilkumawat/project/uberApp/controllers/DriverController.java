package com.anilkumawat.project.uberApp.controllers;

import com.anilkumawat.project.uberApp.dto.*;
import com.anilkumawat.project.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/driver")
public class DriverController {
    private final DriverService driverService;

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping(path = "/startRide/{rideId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideId, @RequestBody StartRideDto startRideDto){
        return ResponseEntity.ok(driverService.startRide(rideId,startRideDto.getOtp()));
    }

    @PostMapping(path = "/rateRider/{rideId}")
    public ResponseEntity<RiderDto> rateRide(@PathVariable Long rideId, @RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(rideId,ratingDto.getRating()));
    }

    @PostMapping(path = "/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @GetMapping(path = "/getMyProfile")
    private ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }


}
