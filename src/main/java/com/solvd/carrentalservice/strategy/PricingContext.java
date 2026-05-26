package com.solvd.carrentalservice.strategy;

import com.solvd.carrentalservice.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PricingContext {

    private static final Logger LOGGER = LogManager.getLogger(PricingContext.class);

    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(Car car, long days) {
        double price = strategy.calculatePrice(car, days);
        LOGGER.info("Pricing strategy: {} | Car: {} {} | Days: {} | Price: {}",
                strategy.getClass().getSimpleName(), car.getBrand(), car.getModel(), days, price);
        return price;
    }
}
