package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
