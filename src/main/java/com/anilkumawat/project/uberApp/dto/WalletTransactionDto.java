package com.anilkumawat.project.uberApp.dto;

import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;
import com.anilkumawat.project.uberApp.entities.enums.TransactionType;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
@Data
@Builder
public class WalletTransactionDto {
    private Long id;
    private WalletDto wallet;
    private RideDto ride;
    private Double amount;
    private TransactionMethod transactionMethod;
    private TransactionType transactionType;
    private Long transactionId;
    private LocalDateTime timeStamp;
}
