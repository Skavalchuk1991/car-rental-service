package com.solvd.carrentalservice.strategy;

import com.solvd.carrentalservice.model.Car;

public class LongTermPricing implements PricingStrategy {
    @Override
    public double calculatePrice(Car car, long days) {
        return car.getDailyRate() * days * 0.85;
    }
}
