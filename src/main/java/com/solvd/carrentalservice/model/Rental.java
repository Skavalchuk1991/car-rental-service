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
    private Car car;
    private Insurance insurance;
    private Payment payment;

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public static class Builder {
        private Long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private double totalPrice;
        private boolean active;
        private LocalDateTime createdAt;
        private Car car;
        private Insurance insurance;
        private Payment payment;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Rental build() {
            Rental rental = new Rental();
            rental.setId(id);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setTotalPrice(totalPrice);
            rental.setActive(active);
            rental.setCreatedAt(createdAt);
            rental.setCar(car);
            rental.setInsurance(insurance);
            rental.setPayment(payment);
            return rental;
        }
    }
}
