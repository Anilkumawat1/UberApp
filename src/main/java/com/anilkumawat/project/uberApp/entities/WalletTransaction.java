package com.anilkumawat.project.uberApp.entities;

import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;
import com.anilkumawat.project.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_wallet_transaction")
@Builder
@ToString
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    @ManyToOne
    private Ride ride;

    private Double amount;

    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    private LocalDateTime timeStamp;

}
