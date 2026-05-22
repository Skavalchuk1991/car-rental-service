package com.solvd.carrentalservice.dao.mybatis;

import com.solvd.carrentalservice.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeMapperDao {
    void create(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    void update(Employee employee);

    void delete(Long id);

    List<Employee> findByBranchId(Long branchId);
}
