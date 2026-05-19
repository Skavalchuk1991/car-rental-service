package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.RentalDao;
import com.solvd.carrentalservice.dao.impl.RentalDaoImpl;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.service.RentalOrderService;

import java.util.List;
import java.util.Optional;

public class RentalOrderServiceImpl implements RentalOrderService {
    private final RentalDao rentalDao = new RentalDaoImpl();

    @Override
    public void create(Rental rental) {
        rentalDao.create(rental);
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return rentalDao.findById(id);
    }

    @Override
    public List<Rental> findAll() {
        return rentalDao.findAll();
    }

    @Override
    public void update(Rental rental) {
        rentalDao.update(rental);
    }

    @Override
    public void delete(Long id) {
        rentalDao.delete(id);
    }

    @Override
    public List<Rental> findByCustomerId(Long customerId) {
        return rentalDao.findByCustomerId(customerId);
    }

    @Override
    public List<Rental> findAllWithDetails() {
        return rentalDao.findAllWithDetails();
    }
}
