package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
}
