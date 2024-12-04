package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RideDto;
import com.anilkumawat.project.uberApp.dto.RideRequestDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.enums.RideRequestStatus;
import com.anilkumawat.project.uberApp.repositories.RideRequestRepository;
import com.anilkumawat.project.uberApp.repositories.RiderRepository;
import com.anilkumawat.project.uberApp.services.RiderService;


import com.anilkumawat.project.uberApp.strategies.DriverMatchingStrategy;
import com.anilkumawat.project.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = modelMapper.map(rideRequestDto,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        double fare = rideFareCalculationStrategy.calculateFare(rideRequest);


        RideRequestDto saved_rideRequest = modelMapper.map(rideRequestRepository.save(rideRequest),RideRequestDto.class);
        driverMatchingStrategy.findMatchingDriver(rideRequest);
//        System.out.println(rideRequest.toString());
//
//        log.info(rideRequest.toString());
        return saved_rideRequest;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public Rider createRider(User user) {
        Rider rider = Rider.builder()
                        .user(user)
                                .rating(0.0).build();
        return riderRepository.save(rider);
    }
}
