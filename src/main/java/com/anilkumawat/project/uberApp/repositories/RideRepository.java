package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
}
