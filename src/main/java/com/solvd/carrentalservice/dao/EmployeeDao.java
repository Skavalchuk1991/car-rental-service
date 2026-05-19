package com.solvd.carrentalservice.dao;

import com.solvd.carrentalservice.model.Employee;

import java.util.List;

public interface EmployeeDao extends GenericDao<Employee> {
    List<Employee> findByBranchId(Long branchId);
}
