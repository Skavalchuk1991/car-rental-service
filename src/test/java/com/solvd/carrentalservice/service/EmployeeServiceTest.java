package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Employee;
import com.solvd.carrentalservice.service.impl.EmployeeServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

@Listeners(com.solvd.carrentalservice.listener.TestNGListener.class)
public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private Long createdEmployeeId;

    @BeforeClass
    public void setUpClass() {
        employeeService = new EmployeeServiceImpl();
    }

    @AfterClass
    public void tearDownClass() {
        if (createdEmployeeId != null) {
            employeeService.delete(createdEmployeeId);
        }
    }

    @Test(priority = 1)
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Alice");
        employee.setLastName("Smith");
        employee.setPosition("Manager");
        employee.setSalary(3000.00);
        employee.setHiredAt(LocalDate.now());

        employeeService.create(employee);
        createdEmployeeId = employee.getId();

        Assert.assertNotNull(employee.getId(), "Employee ID should be generated after creation");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateEmployee")
    public void testFindEmployeeById() {
        Employee found = employeeService.findById(createdEmployeeId).orElse(null);

        Assert.assertNotNull(found, "Employee should be found by ID");
        Assert.assertEquals(found.getFirstName(), "Alice", "First name should match");
        Assert.assertEquals(found.getPosition(), "Manager", "Position should match");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateEmployee")
    public void testUpdateEmployee() {
        Employee employee = employeeService.findById(createdEmployeeId).orElseThrow();
        employee.setSalary(3500.00);
        employeeService.update(employee);

        Employee updated = employeeService.findById(createdEmployeeId).orElseThrow();
        Assert.assertEquals(updated.getSalary(), 3500.00, "Salary should be updated");
    }

    @Test(priority = 4)
    public void testFindAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        Assert.assertNotNull(employees, "Employee list should not be null");
    }

    @Test(priority = 5, dependsOnMethods = "testCreateEmployee")
    public void testEmployeeSoftAssert() {
        Employee found = employeeService.findById(createdEmployeeId).orElse(null);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(found, "Employee should not be null");
        softAssert.assertEquals(found.getLastName(), "Smith", "Last name should match");
        softAssert.assertNotNull(found.getHiredAt(), "HiredAt should not be null");
        softAssert.assertTrue(found.getSalary() > 0, "Salary should be positive");

        softAssert.assertAll();
    }
}
