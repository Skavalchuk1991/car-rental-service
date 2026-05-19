package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.CustomerDao;
import com.solvd.carrentalservice.dao.impl.CustomerDaoImpl;
import com.solvd.carrentalservice.model.Customer;
import com.solvd.carrentalservice.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public void create(Customer customer) {
        customerDao.create(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(Long id) {
        customerDao.delete(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerDao.findByEmail(email);
    }
}
