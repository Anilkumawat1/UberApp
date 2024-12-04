package com.anilkumawat.project.uberApp.strategies.impl;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.services.DistanceService;
import com.anilkumawat.project.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIFICATION;
    }
}
