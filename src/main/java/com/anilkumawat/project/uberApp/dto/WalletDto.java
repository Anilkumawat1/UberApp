package com.anilkumawat.project.uberApp.dto;

import com.anilkumawat.project.uberApp.entities.User;
import lombok.Data;

import java.util.List;
@Data
public class WalletDto {
    private Long id;
    private User userDto;

    private double balance;

    private List<WalletTransactionDto> walletTransaction;
}
