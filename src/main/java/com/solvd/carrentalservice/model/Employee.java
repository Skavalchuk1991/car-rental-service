package com.solvd.carrentalservice.model;

import java.time.LocalDate;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private double salary;
    private LocalDate hiredAt;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public LocalDate getHiredAt() {
        return hiredAt;
    }
    public void setHiredAt(LocalDate hiredAt) {
        this.hiredAt = hiredAt;
    }
}
