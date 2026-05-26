package com.solvd.carrentalservice.facade;

import com.solvd.carrentalservice.listener.RentalEvent;
import com.solvd.carrentalservice.listener.RentalEventManager;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.model.Insurance;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.service.CarService;
import com.solvd.carrentalservice.service.PaymentService;
import com.solvd.carrentalservice.service.RentalOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RentalFacade {

    private static final Logger LOGGER = LogManager.getLogger(RentalFacade.class);

    private final CarService carService;
    private final RentalOrderService rentalOrderService;
    private final PaymentService paymentService;
    private final RentalEventManager eventManager;

    public RentalFacade(CarService carService, RentalOrderService rentalOrderService,
                        PaymentService paymentService, RentalEventManager eventManager) {
        this.carService = carService;
        this.rentalOrderService = rentalOrderService;
        this.paymentService = paymentService;
        this.eventManager = eventManager;
    }

    public Rental createRental(Car car, Insurance insurance, LocalDate start, LocalDate end) {
        LOGGER.info("Facade: Creating rental for {} {} from {} to {}", car.getBrand(), car.getModel(), start, end);

        long days = ChronoUnit.DAYS.between(start, end);
        double totalPrice = days * car.getDailyRate();

        Rental rental = new Rental.Builder()
                .setStartDate(start)
                .setEndDate(end)
                .setTotalPrice(totalPrice)
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setCar(car)
                .setInsurance(insurance)
                .build();

        LOGGER.info("Facade: Rental created, total price: {}", totalPrice);

        eventManager.notify(new RentalEvent("RENTAL_CREATED", rental));

        return rental;
    }
}
