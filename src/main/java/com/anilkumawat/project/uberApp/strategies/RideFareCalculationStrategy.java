package com.anilkumawat.project.uberApp.strategies;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;

public interface RideFareCalculationStrategy {

    double calculateFare(RideRequestDto rideRequestDto);

}
