package com.solvd.carrentalservice.strategy;

import com.solvd.carrentalservice.model.Car;

public class WeekendPricing implements PricingStrategy {
    @Override
    public double calculatePrice(Car car, long days) {
        return car.getDailyRate() * days * 1.25;
    }
}
