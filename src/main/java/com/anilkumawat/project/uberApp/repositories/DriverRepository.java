package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
