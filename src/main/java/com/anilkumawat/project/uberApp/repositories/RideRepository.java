package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RideRepository extends JpaRepository<Ride,Long> {
    @Query(value = "Select count(*) from app_ride as r where r.rider_id : id AND r.ride_status='ENDED'",nativeQuery = true)
    int findTotalRideOfRider(Long id);

    @Query(value = "Select count(*) from app_ride as r where r.driver_id : id AND r.ride_status='ENDED'",nativeQuery = true)
    int findTotalRideOfDriver(Long id);

    Page<Ride> findByRider(Rider rider, Pageable pageRequest);

    Page<Ride> findByDriver(Driver driver, Pageable pageRequest);
}
