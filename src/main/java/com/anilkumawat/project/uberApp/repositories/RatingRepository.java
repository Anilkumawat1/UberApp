package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.dto.RatingDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Rating;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByRide(Ride ride);

    List<Rating> findByDriver(Driver driver);

    List<Rating> findByRider(Rider rider);
}
