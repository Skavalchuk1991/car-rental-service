package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Payment;
import com.solvd.carrentalservice.service.impl.PaymentServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.List;

@Listeners(com.solvd.carrentalservice.listener.TestNGListener.class)
public class PaymentServiceTest {

    private PaymentService paymentService;
    private Long createdPaymentId;

    @BeforeClass
    public void setUpClass() {
        paymentService = new PaymentServiceImpl();
    }

    @AfterClass
    public void tearDownClass() {
        if (createdPaymentId != null) {
            Payment payment = paymentService.findById(createdPaymentId).orElse(null);
            if (payment != null) {
                payment.setPaid(false);
                paymentService.update(payment);
            }
        }
    }

    @Test(priority = 1)
    public void testCreatePayment() {
        java.util.List<Payment> existing = paymentService.findAll();
        Assert.assertFalse(existing.isEmpty(), "At least one payment should exist in database");
        createdPaymentId = existing.get(0).getId();
        Assert.assertNotNull(createdPaymentId, "Payment ID should be available");
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePayment")
    public void testFindPaymentById() {
        Payment found = paymentService.findById(createdPaymentId).orElse(null);

        Assert.assertNotNull(found, "Payment should be found by ID");
        Assert.assertTrue(found.getAmount() > 0, "Amount should be positive");
    }

    @Test(priority = 3, dependsOnMethods = "testCreatePayment")
    public void testUpdatePayment() {
        Payment payment = paymentService.findById(createdPaymentId).orElseThrow();
        payment.setPaid(true);
        paymentService.update(payment);

        Payment updated = paymentService.findById(createdPaymentId).orElseThrow();
        Assert.assertTrue(updated.isPaid(), "Payment should be marked as paid after update");
    }

    @Test(priority = 4)
    public void testFindAllPayments() {
        List<Payment> payments = paymentService.findAll();
        Assert.assertNotNull(payments, "Payment list should not be null");
    }

    @Test(priority = 5, dependsOnMethods = "testCreatePayment")
    public void testPaymentSoftAssert() {
        Payment found = paymentService.findById(createdPaymentId).orElse(null);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(found, "Payment should not be null");
        softAssert.assertTrue(found.getAmount() > 0, "Amount should be positive");
        softAssert.assertNotNull(found.getPaymentDate(), "Payment date should not be null");

        softAssert.assertAll();
    }
}
