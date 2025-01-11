package com.anilkumawat.project.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_rider")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double rating;
}
