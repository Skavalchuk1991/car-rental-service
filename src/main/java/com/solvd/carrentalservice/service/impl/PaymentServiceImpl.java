package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.PaymentDao;
import com.solvd.carrentalservice.dao.impl.PaymentDaoImpl;
import com.solvd.carrentalservice.model.Payment;
import com.solvd.carrentalservice.service.PaymentService;

import java.util.List;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao = new PaymentDaoImpl();

    @Override
    public void create(Payment payment) {
        paymentDao.create(payment);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentDao.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    @Override
    public void update(Payment payment) {
        paymentDao.update(payment);
    }

    @Override
    public void delete(Long id) {
        paymentDao.delete(id);
    }

    @Override
    public Optional<Payment> findByRentalId(Long rentalId) {
        return paymentDao.findByRentalId(rentalId);
    }
}
