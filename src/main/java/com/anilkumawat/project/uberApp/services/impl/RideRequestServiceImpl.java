package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.entities.RideRequest;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.RideRequestRepository;
import com.anilkumawat.project.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;
    private final ModelMapper modelMapper;

    @Override
    public RideRequest findById(Long rideRequestId) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId).orElseThrow(()-> new ResourceNotFoundException("Ride request not found with id: "+rideRequestId));
        return rideRequest;
    }

    @Override
    public void update(RideRequest rideRequest){
        rideRequestRepository.findById(rideRequest.getId()).orElseThrow(()->new ResourceNotFoundException("Ride request not found with id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
