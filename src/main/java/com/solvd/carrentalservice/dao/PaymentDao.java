package com.solvd.carrentalservice.dao;

import com.solvd.carrentalservice.model.Payment;

import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment> {
    Optional<Payment> findByRentalId(Long rentalId);
}
