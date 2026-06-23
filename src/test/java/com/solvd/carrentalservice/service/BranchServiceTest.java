package com.solvd.carrentalservice.service;

import com.solvd.carrentalservice.model.Branch;
import com.solvd.carrentalservice.service.impl.BranchServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.List;

@Listeners(com.solvd.carrentalservice.listener.TestNGListener.class)
public class BranchServiceTest {

    private BranchService branchService;
    private Long createdBranchId;

    @BeforeClass
    public void setUpClass() {
        branchService = new BranchServiceImpl();
    }

    @AfterClass
    public void tearDownClass() {
        if (createdBranchId != null) {
            branchService.delete(createdBranchId);
        }
    }

    @Test(priority = 1)
    public void testCreateBranch() {
        Branch branch = new Branch();
        branch.setName("Test Branch");
        branch.setAddress("123 Test Street");
        branch.setCity("Batumi");
        branch.setOpenedAt(LocalDateTime.of(2023, 1, 15, 9, 0));

        branchService.create(branch);
        createdBranchId = branch.getId();

        Assert.assertNotNull(branch.getId(), "Branch ID should be generated after creation");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateBranch")
    public void testFindBranchById() {
        Branch found = branchService.findById(createdBranchId).orElse(null);

        Assert.assertNotNull(found, "Branch should be found by ID");
        Assert.assertEquals(found.getName(), "Test Branch", "Branch name should match");
        Assert.assertEquals(found.getCity(), "Batumi", "City should match");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateBranch")
    public void testUpdateBranch() {
        Branch branch = branchService.findById(createdBranchId).orElseThrow();
        branch.setCity("Tbilisi");
        branchService.update(branch);

        Branch updated = branchService.findById(createdBranchId).orElseThrow();
        Assert.assertEquals(updated.getCity(), "Tbilisi", "City should be updated to Tbilisi");
    }

    @Test(priority = 4)
    public void testFindAllBranches() {
        List<Branch> branches = branchService.findAll();
        Assert.assertNotNull(branches, "Branch list should not be null");
    }

    @Test(priority = 5, dependsOnMethods = "testCreateBranch")
    public void testBranchSoftAssert() {
        Branch found = branchService.findById(createdBranchId).orElse(null);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(found, "Branch should not be null");
        softAssert.assertNotNull(found.getName(), "Branch name should not be null");
        softAssert.assertNotNull(found.getAddress(), "Branch address should not be null");
        softAssert.assertNotNull(found.getOpenedAt(), "OpenedAt should not be null");

        softAssert.assertAll();
    }
}
