package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Customer;
import com.solvd.carrentalservice.service.impl.CustomerServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.List;

@Listeners(com.solvd.carrentalservice.listener.TestNGListener.class)
public class CustomerServiceTest {

    private CustomerService customerService;
    private Long createdCustomerId;

    @BeforeClass
    public void setUpClass() {
        customerService = new CustomerServiceImpl();
    }

    @AfterClass
    public void tearDownClass() {
        if (createdCustomerId != null) {
            customerService.delete(createdCustomerId);
        }
    }

    @Test(priority = 1)
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe.test@example.com");
        customer.setPhone("+1234567890");
        customer.setVerified(true);
        customer.setRegisteredAt(LocalDateTime.now());

        customerService.create(customer);
        createdCustomerId = customer.getId();

        Assert.assertNotNull(customer.getId(), "Customer ID should be generated after creation");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateCustomer")
    public void testFindCustomerById() {
        Customer found = customerService.findById(createdCustomerId).orElse(null);

        Assert.assertNotNull(found, "Customer should be found by ID");
        Assert.assertEquals(found.getFirstName(), "John", "First name should match");
        Assert.assertEquals(found.getLastName(), "Doe", "Last name should match");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateCustomer")
    public void testUpdateCustomer() {
        Customer customer = customerService.findById(createdCustomerId).orElseThrow();
        customer.setPhone("+9999999999");
        customerService.update(customer);

        Customer updated = customerService.findById(createdCustomerId).orElseThrow();
        Assert.assertEquals(updated.getPhone(), "+9999999999", "Phone should be updated");
    }

    @Test(priority = 4)
    public void testFindAllCustomers() {
        List<Customer> customers = customerService.findAll();
        Assert.assertNotNull(customers, "Customer list should not be null");
    }

    @Test(priority = 5, dependsOnMethods = "testCreateCustomer")
    public void testCustomerSoftAssert() {
        Customer found = customerService.findById(createdCustomerId).orElse(null);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(found, "Customer should not be null");
        softAssert.assertEquals(found.getEmail(), "john.doe.test@example.com", "Email should match");
        softAssert.assertTrue(found.isVerified(), "Customer should be verified");
        softAssert.assertNotNull(found.getRegisteredAt(), "RegisteredAt should not be null");

        softAssert.assertAll();
    }
}
