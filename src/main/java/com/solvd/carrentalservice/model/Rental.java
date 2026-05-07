package com.solvd.carrentalservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rental {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean active;
    private LocalDateTime createdAt;
    private Long customerId;
    private Long carId;
    private Long insuranceId;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getCarId() {
        return carId;
    }
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    public Long getInsuranceId() {
        return insuranceId;
    }
    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
}
