package com.solvd.carrentalservice.dao.mybatis;

import com.solvd.carrentalservice.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentMapperDao {
    void create(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    void update(Payment payment);

    void delete(Long id);

    Optional<Payment> findByRentalId(Long rentalId);
}
