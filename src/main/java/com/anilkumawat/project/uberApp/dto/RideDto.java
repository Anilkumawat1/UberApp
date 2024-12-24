package com.anilkumawat.project.uberApp.dto;

import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.enums.PaymentMethod;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {

    private Long id;
    private PointDto pickLocation;
    private PointDto dropLocation;
    private RiderDto rider;
    private DriverDto driver;

    private Double fare;

    private String otp;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
