package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query(value = "Select d.*,ST_Distance(d.current_location,:pickLocation) as distance "
    +"from app_driver as d "+
            "WHERE d.is_available = true AND ST_DWithin(d.current_location,:pickLocation,1000) "+
            "ORDER BY distance LIMIT 10",nativeQuery = true)
    List<Driver> findTenNearestDriver(Point pickLocation);

    @Query(value = "Select d.*,ST_Distance(d.current_location,:pickLocation) as distance "
            +"from app_driver as d "+
            "WHERE d.is_available = true AND ST_DWithin(d.current_location,:pickLocation,1500) "+
            "ORDER BY d.rating DESC LIMIT 10",nativeQuery = true)
    List<Driver> findTenNearestAndTopRatedDriver(Point pickLocation);
}
