package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.entities.Payment;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.enums.PaymentStatus;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.repositories.PaymentRepository;
import com.anilkumawat.project.uberApp.services.PaymentService;
import com.anilkumawat.project.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride).orElseThrow(()-> new ResourceNotFoundException("Ride not found with id: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createPayment(Ride ride) {
        Payment payment = Payment.builder()
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .amount(ride.getFare())
                .ride(ride)
                .build();
        return savePayment(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment,PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        savePayment(payment);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
