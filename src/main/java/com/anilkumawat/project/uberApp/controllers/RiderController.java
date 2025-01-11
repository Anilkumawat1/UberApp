package com.anilkumawat.project.uberApp.controllers;

import com.anilkumawat.project.uberApp.dto.*;
import com.anilkumawat.project.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {
    private final RiderService riderService;

    @PostMapping(path = "/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        System.out.println(rideRequestDto.getRideRequestStatus());
        return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }

    @PostMapping(path = "/rateRider/{rideId}")
    public ResponseEntity<DriverDto> rateRide(@PathVariable Long rideId, @RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(riderService.rateDriver(rideId,ratingDto.getRating()));
    }

    @PostMapping(path = "/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @GetMapping(path = "/getMyProfile")
    private ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping(path = "/getAllMyRides")
    private ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"creationTime","id"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }
}
