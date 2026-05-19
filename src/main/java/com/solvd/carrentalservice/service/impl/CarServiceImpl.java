package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.CarDao;
import com.solvd.carrentalservice.dao.impl.CarDaoImpl;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.service.CarService;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {
    private final CarDao carDao = new CarDaoImpl();

    @Override
    public void create(Car car) {
        carDao.create(car);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public void update(Car car) {
        carDao.update(car);
    }

    @Override
    public void delete(Long id) {
        carDao.delete(id);
    }

    @Override
    public List<Car> findByBranchId(Long branchId) {
        return carDao.findByBranchId(branchId);
    }
}
