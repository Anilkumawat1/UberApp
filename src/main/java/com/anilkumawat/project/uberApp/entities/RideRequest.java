package com.anilkumawat.project.uberApp.entities;

import com.anilkumawat.project.uberApp.entities.enums.PaymentMethod;
import com.anilkumawat.project.uberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;


import java.time.LocalDateTime;


@Entity
@Table(name = "app_ride_request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class RideRequest {
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

    private Double fare;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

    @CreationTimestamp
    private LocalDateTime requestedTime;

}
