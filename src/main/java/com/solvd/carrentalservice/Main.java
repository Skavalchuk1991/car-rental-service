package com.solvd.carrentalservice;

import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.service.CarService;
import com.solvd.carrentalservice.service.CustomerService;
import com.solvd.carrentalservice.service.RentalOrderService;
import com.solvd.carrentalservice.service.impl.CarServiceImpl;
import com.solvd.carrentalservice.service.impl.CustomerServiceImpl;
import com.solvd.carrentalservice.service.impl.RentalOrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Car Rental Service started");

        CarService carService = new CarServiceImpl();
        List<Car> cars = carService.findAll();
        LOGGER.info("Total cars: {}", cars.size());
        cars.forEach(c -> LOGGER.info("  {} {} ({})", c.getBrand(), c.getModel(), c.getYear()));

        CustomerService customerService = new CustomerServiceImpl();
        customerService.findAll().forEach(c -> LOGGER.info("Customer: {} {}", c.getFirstName(), c.getLastName()));

        RentalOrderService rentalService = new RentalOrderServiceImpl();
        List<Rental> details = rentalService.findAllWithDetails();
        details.forEach(r -> LOGGER.info("Rental #{}: {} {} | Car: {} {} | Price: {}",
                r.getId(), r.getStartDate(), r.getEndDate(),
                r.getCar().getBrand(), r.getCar().getModel(), r.getTotalPrice()));

        LOGGER.info("Car Rental Service finished");
    }
}
