package com.anilkumawat.project.uberApp.strategies.impl;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequest rideRequest) {
        return 0;
    }
}
