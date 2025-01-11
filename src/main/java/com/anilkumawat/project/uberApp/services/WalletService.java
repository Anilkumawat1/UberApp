package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.Wallet;
import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, Long transactionId, Ride ride, TransactionMethod transactionMethod);
    Wallet deductMoneyFromWallet(User user, Double amount, Long transactionId, Ride ride, TransactionMethod transactionMethod);
    void getMyMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
