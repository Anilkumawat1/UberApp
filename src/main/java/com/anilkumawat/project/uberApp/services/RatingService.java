package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.Ride;

public interface RatingService {
    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
