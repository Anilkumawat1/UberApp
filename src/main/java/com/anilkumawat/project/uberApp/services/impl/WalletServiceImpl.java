package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.Wallet;
import com.anilkumawat.project.uberApp.entities.WalletTransaction;
import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;
import com.anilkumawat.project.uberApp.entities.enums.TransactionType;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.WalletRepository;
import com.anilkumawat.project.uberApp.services.WalletService;
import com.anilkumawat.project.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, Long transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .ride(ride)
                .amount(amount)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);

       // System.out.println(wallet);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, Long transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .ride(ride)
                .amount(amount)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);
       // System.out.println(wallet);
        return walletRepository.save(wallet);
    }


    @Override
    public void getMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()-> new ResourceNotFoundException("Wallet Not found with id: "+walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("User not found with id: "+user.getId()));
    }

}
