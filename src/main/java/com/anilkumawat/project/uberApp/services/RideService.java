package com.anilkumawat.project.uberApp.services;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);

    //Rider rateRider(Rider rider, Integer rating);
    Ride update(Ride ride);

    //Driver rateDriver(Driver driver,Integer rating);
}
