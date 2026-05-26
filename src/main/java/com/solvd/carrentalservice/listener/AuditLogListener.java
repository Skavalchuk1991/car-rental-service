package com.solvd.carrentalservice.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditLogListener implements RentalEventListener {

    private static final Logger LOGGER = LogManager.getLogger(AuditLogListener.class);

    @Override
    public void onEvent(RentalEvent event) {
        LOGGER.info("AUDIT: Rental {} - event '{}' logged at {}",
                event.getRental().getId(), event.getType(), java.time.LocalDateTime.now());
    }
}
