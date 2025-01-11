package com.anilkumawat.project.uberApp.services;

import com.anilkumawat.project.uberApp.entities.Payment;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);

    Payment savePayment(Payment payment);
}
