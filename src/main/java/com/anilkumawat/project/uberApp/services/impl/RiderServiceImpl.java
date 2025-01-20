package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RideDto;
import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.*;
import com.anilkumawat.project.uberApp.entities.enums.RideRequestStatus;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.RideRequestRepository;
import com.anilkumawat.project.uberApp.repositories.RiderRepository;
import com.anilkumawat.project.uberApp.services.DriverService;
import com.anilkumawat.project.uberApp.services.RatingService;
import com.anilkumawat.project.uberApp.services.RideService;
import com.anilkumawat.project.uberApp.services.RiderService;


import com.anilkumawat.project.uberApp.strategies.DriverMatchingStrategy;
import com.anilkumawat.project.uberApp.strategies.RideFareCalculationStrategy;
import com.anilkumawat.project.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider currentRider = currentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        rideRequest.setRider(currentRider);
        RideRequestDto saved_rideRequest = modelMapper.map(rideRequestRepository.save(rideRequest),RideRequestDto.class);
        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(currentRider.getRating()).findMatchingDriver(rideRequest);
        //sending email to all the driver that accept the request
        return saved_rideRequest;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Rider currentRider = currentRider();
        if(!ride.getRider().equals(currentRider)){
            throw new RuntimeException("Rider dose not own the ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.CANCELED);
        driverService.updateAvailability(ride.getDriver(),true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider currentRider = currentRider();
        if(!ride.getRider().equals(currentRider)){
            throw new RuntimeException("Rider cannot rate the ride, Not owned by the rider");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride is not ended hence can't rate, invalid status :"+ride.getRideStatus());
        }
        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDto getMyProfile() {
        return modelMapper.map(currentRider(),RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = currentRider();
        return rideService.getAllRidesOfRider(currentRider,pageRequest).map(
                ride -> modelMapper.map(ride,RideDto.class)
        );
    }

    @Override
    public Rider createRider(User user) {
        Rider rider = Rider.builder()
                        .user(user).build();
        System.out.println(rider+"--------------------------------------------");
        return riderRepository.save(rider);
    }

    @Override
    public Rider currentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rider rider = riderRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException( "Rider is not associated with user with id: "+user.getId()));;
        return rider;
    }
}
