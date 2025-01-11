package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.entities.WalletTransaction;
import com.anilkumawat.project.uberApp.repositories.WalletTransactionRepository;
import com.anilkumawat.project.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
