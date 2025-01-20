package com.anilkumawat.project.uberApp.controllers;

import com.anilkumawat.project.uberApp.dto.*;
import com.anilkumawat.project.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
@Secured("ROLE_DRIVER")
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

    @PostMapping(path = "/endRide/{rideId}")
    private ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @GetMapping(path = "/getAllMyRides")
    private ResponseEntity<Page<RideDto>> getAllRide(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"creationTime","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }
}
