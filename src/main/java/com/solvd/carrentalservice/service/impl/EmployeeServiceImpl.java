package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.EmployeeDao;
import com.solvd.carrentalservice.dao.impl.EmployeeDaoImpl;
import com.solvd.carrentalservice.model.Employee;
import com.solvd.carrentalservice.service.EmployeeService;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void create(Employee employee) {
        employeeDao.create(employee);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDao.delete(id);
    }

    @Override
    public List<Employee> findByBranchId(Long branchId) {
        return employeeDao.findByBranchId(branchId);
    }
}
