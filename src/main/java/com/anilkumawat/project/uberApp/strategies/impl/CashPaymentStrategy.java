package com.anilkumawat.project.uberApp.strategies.impl;

import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Payment;
import com.anilkumawat.project.uberApp.entities.Wallet;
import com.anilkumawat.project.uberApp.entities.enums.PaymentStatus;
import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;
import com.anilkumawat.project.uberApp.repositories.PaymentRepository;
import com.anilkumawat.project.uberApp.services.PaymentService;
import com.anilkumawat.project.uberApp.services.WalletService;
import com.anilkumawat.project.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Wallet driverWallet= walletService.findByUser(driver.getUser());
        Double platform_charge = payment.getAmount() * PLATFORM_CHARGE;
        walletService.deductMoneyFromWallet(driver.getUser(),payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
