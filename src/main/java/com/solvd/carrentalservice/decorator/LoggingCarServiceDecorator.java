package com.solvd.carrentalservice.decorator;

import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class LoggingCarServiceDecorator implements CarService {

    private static final Logger LOGGER = LogManager.getLogger(LoggingCarServiceDecorator.class);

    private final CarService delegate;

    public LoggingCarServiceDecorator(CarService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void create(Car car) {
        LOGGER.info("[DECORATOR] Creating car: {} {}", car.getBrand(), car.getModel());
        long start = System.currentTimeMillis();
        delegate.create(car);
        LOGGER.info("[DECORATOR] Car created in {} ms", System.currentTimeMillis() - start);
    }

    @Override
    public Optional<Car> findById(Long id) {
        LOGGER.info("[DECORATOR] Finding car by id: {}", id);
        return delegate.findById(id);
    }

    @Override
    public List<Car> findAll() {
        LOGGER.info("[DECORATOR] Finding all cars");
        List<Car> cars = delegate.findAll();
        LOGGER.info("[DECORATOR] Found {} cars", cars.size());
        return cars;
    }

    @Override
    public void update(Car car) {
        LOGGER.info("[DECORATOR] Updating car id: {}", car.getId());
        delegate.update(car);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("[DECORATOR] Deleting car id: {}", id);
        delegate.delete(id);
    }

    @Override
    public List<Car> findByBranchId(Long branchId) {
        LOGGER.info("[DECORATOR] Finding cars by branch: {}", branchId);
        return delegate.findByBranchId(branchId);
    }
}
