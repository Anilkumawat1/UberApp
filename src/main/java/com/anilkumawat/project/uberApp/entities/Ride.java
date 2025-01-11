package com.anilkumawat.project.uberApp.entities;

import com.anilkumawat.project.uberApp.entities.enums.PaymentMethod;
import com.anilkumawat.project.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;


import java.time.LocalDateTime;

@Entity
@Table(name = "app_ride",
indexes={
        @Index(name = "idx_ride_rider",columnList = "rider_id"),
        @Index(name = "idx_ride_driver",columnList = "driver_id")

})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @CreationTimestamp
    private LocalDateTime creationTime;
    private Double fare;

    private String otp;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;


}
