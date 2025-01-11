package com.anilkumawat.project.uberApp.strategies;

import com.anilkumawat.project.uberApp.entities.Payment;

public interface PaymentStrategy {
    static final Double PLATFORM_CHARGE = 0.3;
    void processPayment(Payment payment);
}
