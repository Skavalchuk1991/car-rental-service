package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalOrderService {
    void create(Rental rental);

    Optional<Rental> findById(Long id);

    List<Rental> findAll();

    void update(Rental rental);

    void delete(Long id);

    List<Rental> findByCustomerId(Long customerId);

    List<Rental> findAllWithDetails();
}
