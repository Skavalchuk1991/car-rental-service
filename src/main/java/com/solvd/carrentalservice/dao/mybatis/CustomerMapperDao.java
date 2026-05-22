package com.solvd.carrentalservice.dao.mybatis;

import com.solvd.carrentalservice.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerMapperDao {
    void create(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    void update(Customer customer);

    void delete(Long id);

    Optional<Customer> findByEmail(String email);
}
