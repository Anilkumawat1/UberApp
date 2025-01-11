package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.dto.WalletTransactionDto;
import com.anilkumawat.project.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {
    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
