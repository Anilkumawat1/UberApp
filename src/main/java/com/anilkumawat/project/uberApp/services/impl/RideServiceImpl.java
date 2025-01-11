package com.anilkumawat.project.uberApp.services.impl;


import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.enums.RideRequestStatus;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.DriverRepository;
import com.anilkumawat.project.uberApp.repositories.RideRepository;
import com.anilkumawat.project.uberApp.repositories.RiderRepository;
import com.anilkumawat.project.uberApp.services.RideRequestService;
import com.anilkumawat.project.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final RideRequestService rideRequestService;
    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(()->new ResourceNotFoundException("Ride not found with id: "+rideId));
        return ride;
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest,Ride.class);
        ride.setDriver(driver);
        ride.setOtp(generateOtp());
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setId(null);
        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider,pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver,pageRequest);
    }

//    @Override
//    public Rider rateRider(Rider rider, Integer rating) {
//        int total_ride = rideRepository.findTotalRideOfRider(rider.getId());
//        double new_rating = (total_ride*rider.getRating() + rating)/(total_ride+1);
//        rider.setRating(new_rating);
//        return riderRepository.save(rider);
//    }

    @Override
    public Ride update(Ride ride) {
        return rideRepository.save(ride);
    }

//    @Override
//    public Driver rateDriver(Driver driver,Integer rating) {
//        int total_ride = rideRepository.findTotalRideOfDriver(driver.getId());
//        double new_rating = (total_ride*driver.getRating() + rating) / (total_ride + 1);
//        driver.setRating(new_rating);
//        return driverRepository.save(driver);
//    }

    private String generateOtp(){
        Random random = new Random();
        int otp = random.nextInt(0,10000);
        return String.format("%04d",otp);
    }
}
