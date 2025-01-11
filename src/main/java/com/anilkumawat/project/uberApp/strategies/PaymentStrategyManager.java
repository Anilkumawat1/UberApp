package com.anilkumawat.project.uberApp.strategies;

import com.anilkumawat.project.uberApp.entities.enums.PaymentMethod;
import com.anilkumawat.project.uberApp.strategies.impl.CashPaymentStrategy;
import com.anilkumawat.project.uberApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case CASH -> cashPaymentStrategy;
            case WALLET -> walletPaymentStrategy;
        };
    }
}
