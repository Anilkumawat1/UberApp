package com.anilkumawat.project.uberApp.services.impl;


import com.anilkumawat.project.uberApp.services.DistanceService;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {
    // we will call the osrm api for distance calculation
    @Override
    public double calculateDistance(Point src, Point dest) {
        return 0;
    }
}
