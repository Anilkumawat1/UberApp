package com.anilkumawat.project.uberApp.strategies;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    static final double RIDE_FARE_MULTIFICATION = 10.0;
    double calculateFare(RideRequest rideRequest);

}
