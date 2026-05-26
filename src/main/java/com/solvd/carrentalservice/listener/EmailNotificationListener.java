package com.solvd.carrentalservice.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailNotificationListener implements RentalEventListener {

    private static final Logger LOGGER = LogManager.getLogger(EmailNotificationListener.class);

    @Override
    public void onEvent(RentalEvent event) {
        LOGGER.info("EMAIL: Rental {} - event '{}' | Price: {}",
                event.getRental().getId(), event.getType(), event.getRental().getTotalPrice());
    }
}
