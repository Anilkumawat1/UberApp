package com.anilkumawat.project.uberApp.strategies.impl;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.services.DistanceService;
import com.anilkumawat.project.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    private final DistanceService distanceService;
    private static final Double SURGEFACTOR = 2.0;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIFICATION*SURGEFACTOR;
    }
}
