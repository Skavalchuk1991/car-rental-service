package com.solvd.carrentalservice.dao;

import com.solvd.carrentalservice.model.Customer;

import java.util.Optional;

public interface CustomerDao extends GenericDao<Customer> {
    Optional<Customer> findByEmail(String email);
}
