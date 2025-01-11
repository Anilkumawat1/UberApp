package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Payment;
import com.anilkumawat.project.uberApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByRide(Ride ride);
}
