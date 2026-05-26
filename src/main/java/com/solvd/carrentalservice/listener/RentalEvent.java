package com.solvd.carrentalservice.listener;

import com.solvd.carrentalservice.model.Rental;

public class RentalEvent {
    private final String type;
    private final Rental rental;

    public RentalEvent(String type, Rental rental) {
        this.type = type;
        this.rental = rental;
    }

    public String getType() {
        return type;
    }

    public Rental getRental() {
        return rental;
    }
}
