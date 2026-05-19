package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    void create(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    void update(Payment payment);

    void delete(Long id);

    Optional<Payment> findByRentalId(Long rentalId);
}
