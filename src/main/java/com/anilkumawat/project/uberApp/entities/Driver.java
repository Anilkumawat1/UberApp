package com.anilkumawat.project.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "app_driver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double rating;

    private Boolean isAvailable;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
    private String vehicleId;
}
