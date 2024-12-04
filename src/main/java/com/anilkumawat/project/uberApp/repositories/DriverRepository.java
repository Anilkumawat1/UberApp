package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query(value = "Select d.*,ST_Distance(d.currentLocation,:pickLocation) as distance "
    +"from app_driver as d "+
            "WHERE d.isAvailable = true AND ST_DWithin(d.currentLocation,:pickLocation,1000) "+
            "ORDER BY distance LIMIT 10",nativeQuery = true)
    List<Driver> findMatchingDriver(Point pickLocation);
}
