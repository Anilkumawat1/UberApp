package com.anilkumawat.project.uberApp.strategies.impl;

import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Payment;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.entities.enums.PaymentStatus;
import com.anilkumawat.project.uberApp.entities.enums.TransactionMethod;
import com.anilkumawat.project.uberApp.repositories.PaymentRepository;
import com.anilkumawat.project.uberApp.services.WalletService;
import com.anilkumawat.project.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();
        Double platform_charge = payment.getAmount() * PLATFORM_CHARGE;
        walletService.deductMoneyFromWallet(rider.getUser(),payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);
        walletService.addMoneyToWallet(driver.getUser(),payment.getAmount()-platform_charge,null,payment.getRide(),TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
