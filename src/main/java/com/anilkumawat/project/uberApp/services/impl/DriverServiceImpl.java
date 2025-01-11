package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RideDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.enums.RideRequestStatus;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.DriverRepository;
import com.anilkumawat.project.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final RatingService ratingService;
    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findById(rideRequestId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request can not be accepted, status : "+rideRequest.getRideRequestStatus());
        }
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getIsAvailable()){
            throw new RuntimeException("Driver can not accept the ride due to unavailability");
        }
        Driver savedDriver = updateAvailability(currentDriver,false);
        Ride ride = rideService.createNewRide(rideRequest,savedDriver);
        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel a ride as he has not accepted earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }
        updateAvailability(currentDriver,true);
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.CANCELED);
        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto startRide(Long rideId,String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not confirmed hence cannot be started ,status: "+ride.getRideStatus());
        }
        if(!ride.getOtp().equals(otp)){
            throw new RuntimeException("Otp is not valid ,otp: "+otp);
        }
        ride.setStartedAt(LocalDateTime.now());
        paymentService.createPayment(ride);
        ratingService.createNewRating(ride);
        return modelMapper.map(rideService.updateRideStatus(ride,RideStatus.ONGOING),RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not Ongoing hence cannot be ended ,status: "+ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateAvailability(currentDriver,true);
        paymentService.processPayment(savedRide);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot rate a ride as he has not accepted earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride is not ended hence you can't rate, status : "+ride.getRideStatus());
        }
        return ratingService.rateRider(ride,rating);
    }

    @Override
    public DriverDto getMyProfile() {
        return modelMapper.map(getCurrentDriver(),DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver,pageRequest).map(
                ride -> modelMapper.map(ride,RideDto.class)
        );
    }

    @Override
    public Driver updateAvailability(Driver driver, boolean availability) {
        driver.setIsAvailable(availability);
        return driverRepository.save(driver);
    }
    @Override
    public Driver getCurrentDriver() {
        //this will be implemented letter by using spring security
        return driverRepository.findById(2L).orElseThrow(()-> new ResourceNotFoundException("Driver not found"));
    }

    @Override
    public Driver createNewDriver(User user) {
        Driver driver = Driver.builder()
                .user(user)
                .isAvailable(true)
                .build();
        return driverRepository.save(driver);
    }
}
