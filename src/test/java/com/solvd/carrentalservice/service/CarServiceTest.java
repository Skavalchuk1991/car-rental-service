package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.model.Category;
import com.solvd.carrentalservice.service.impl.CarServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.List;

@Listeners(com.solvd.carrentalservice.listener.TestNGListener.class)
public class CarServiceTest {

    private CarService carService;
    private Long createdCarId;

    @BeforeClass
    public void setUpClass() {
        carService = new CarServiceImpl();
    }

    @BeforeMethod
    public void beforeMethod() {
    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterClass
    public void tearDownClass() {
        if (createdCarId != null) {
            carService.delete(createdCarId);
        }
    }

    @Test(priority = 1)
    public void testCreateCar() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2022);
        car.setDailyRate(55.00);
        car.setAvailable(true);
        car.setCreatedAt(LocalDateTime.now());
        Category category = new Category();
        category.setId(1L);
        car.setCategory(category);

        carService.create(car);
        createdCarId = car.getId();

        Assert.assertNotNull(car.getId(), "Car ID should be generated after creation");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateCar")
    public void testFindCarById() {
        Car found = carService.findById(createdCarId).orElse(null);

        Assert.assertNotNull(found, "Car should be found by ID");
        Assert.assertEquals(found.getBrand(), "Toyota", "Brand should match");
        Assert.assertEquals(found.getModel(), "Camry", "Model should match");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateCar")
    public void testUpdateCar() {
        Car car = carService.findById(createdCarId).orElseThrow();
        car.setDailyRate(65.00);
        carService.update(car);

        Car updated = carService.findById(createdCarId).orElseThrow();
        Assert.assertEquals(updated.getDailyRate(), 65.00, "Daily rate should be updated");
    }

    @Test(priority = 4)
    public void testFindAllCars() {
        List<Car> cars = carService.findAll();
        Assert.assertNotNull(cars, "Car list should not be null");
        Assert.assertTrue(cars.size() >= 0, "Car list size should be non-negative");
    }

    @Test(priority = 5, dependsOnMethods = "testCreateCar")
    public void testCarSoftAssert() {
        Car found = carService.findById(createdCarId).orElse(null);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(found, "Car should not be null");
        softAssert.assertEquals(found.getBrand(), "Toyota", "Brand should be Toyota");
        softAssert.assertTrue(found.isAvailable(), "Car should be available");
        softAssert.assertNotNull(found.getCreatedAt(), "CreatedAt should not be null");

        softAssert.assertAll();
    }
}
