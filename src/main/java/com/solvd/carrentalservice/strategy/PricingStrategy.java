package com.solvd.carrentalservice.strategy;

import com.solvd.carrentalservice.model.Car;

public interface PricingStrategy {
    double calculatePrice(Car car, long days);
}
