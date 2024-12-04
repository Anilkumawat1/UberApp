package com.anilkumawat.project.uberApp.services;



import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RideDto;
import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    Rider createRider(User user);
}
