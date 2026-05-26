package com.solvd.carrentalservice;

import com.solvd.carrentalservice.decorator.LoggingCarServiceDecorator;
import com.solvd.carrentalservice.facade.RentalFacade;
import com.solvd.carrentalservice.factory.CarDaoFactory;
import com.solvd.carrentalservice.factory.DaoAbstractFactory;
import com.solvd.carrentalservice.factory.DaoType;
import com.solvd.carrentalservice.factory.JdbcDaoFactory;
import com.solvd.carrentalservice.listener.AuditLogListener;
import com.solvd.carrentalservice.listener.EmailNotificationListener;
import com.solvd.carrentalservice.listener.RentalEventManager;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.model.Insurance;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.service.CarService;
import com.solvd.carrentalservice.service.PaymentService;
import com.solvd.carrentalservice.service.RentalOrderService;
import com.solvd.carrentalservice.service.impl.CarServiceImpl;
import com.solvd.carrentalservice.service.impl.PaymentServiceImpl;
import com.solvd.carrentalservice.service.impl.RentalOrderServiceImpl;
import com.solvd.carrentalservice.strategy.LongTermPricing;
import com.solvd.carrentalservice.strategy.PricingContext;
import com.solvd.carrentalservice.strategy.StandardPricing;
import com.solvd.carrentalservice.strategy.WeekendPricing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("=== Car Rental Service — Design Patterns Demo ===\n");

        LOGGER.info("--- 1. Factory Pattern ---");
        var jdbcCarDao = CarDaoFactory.createCarDao(DaoType.JDBC);
        LOGGER.info("JDBC CarDao created: {}", jdbcCarDao.getClass().getSimpleName());

        LOGGER.info("\n--- 2. Abstract Factory Pattern ---");
        DaoAbstractFactory factory = new JdbcDaoFactory();
        var carDao = factory.createCarDao();
        var customerDao = factory.createCustomerDao();
        LOGGER.info("Abstract Factory created CarDao: {}, CustomerDao: {}",
                carDao.getClass().getSimpleName(), customerDao.getClass().getSimpleName());

        LOGGER.info("\n--- 3. Builder Pattern ---");
        Rental rental = new Rental.Builder()
                .setStartDate(LocalDate.of(2025, 6, 1))
                .setEndDate(LocalDate.of(2025, 6, 5))
                .setTotalPrice(250.0)
                .setActive(true)
                .build();
        LOGGER.info("Built rental: {} to {} | Price: {}", rental.getStartDate(), rental.getEndDate(), rental.getTotalPrice());

        LOGGER.info("\n--- 4. Observer/Listener Pattern ---");
        RentalEventManager eventManager = new RentalEventManager();
        eventManager.subscribe(new EmailNotificationListener());
        eventManager.subscribe(new AuditLogListener());

        LOGGER.info("\n--- 5. Decorator Pattern ---");
        CarService carService = new LoggingCarServiceDecorator(new CarServiceImpl());
        List<Car> cars = carService.findAll();
        LOGGER.info("Decorated service returned {} cars", cars.size());

        LOGGER.info("\n--- 6. Strategy Pattern ---");
        Car demoCar = new Car();
        demoCar.setBrand("Toyota");
        demoCar.setModel("Corolla");
        demoCar.setDailyRate(45.0);

        PricingContext pricing = new PricingContext(new StandardPricing());
        pricing.calculate(demoCar, 5);

        pricing.setStrategy(new WeekendPricing());
        pricing.calculate(demoCar, 5);

        pricing.setStrategy(new LongTermPricing());
        pricing.calculate(demoCar, 30);

        LOGGER.info("\n--- 7. Facade Pattern ---");
        RentalOrderService rentalService = new RentalOrderServiceImpl();
        PaymentService paymentService = new PaymentServiceImpl();
        RentalFacade facade = new RentalFacade(carService, rentalService, paymentService, eventManager);

        if (!cars.isEmpty()) {
            Car firstCar = cars.get(0);
            Rental facadeRental = facade.createRental(firstCar, null,
                    LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 5));
            LOGGER.info("Facade created rental with price: {}", facadeRental.getTotalPrice());
        }

        LOGGER.info("\n=== All patterns demonstrated ===");
    }
}
