package com.solvd.carrentalservice.listener;

import java.util.ArrayList;
import java.util.List;

public class RentalEventManager {

    private final List<RentalEventListener> listeners = new ArrayList<>();

    public void subscribe(RentalEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(RentalEventListener listener) {
        listeners.remove(listener);
    }

    public void notify(RentalEvent event) {
        listeners.forEach(listener -> listener.onEvent(event));
    }
}
