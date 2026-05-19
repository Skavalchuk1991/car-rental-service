package com.solvd.carrentalservice.dao;

import com.solvd.carrentalservice.model.Rental;

import java.util.List;

public interface RentalDao extends GenericDao<Rental> {
    List<Rental> findByCustomerId(Long customerId);

    List<Rental> findAllWithDetails();
}
