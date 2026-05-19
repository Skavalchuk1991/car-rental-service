package com.solvd.carrentalservice.dao;

import com.solvd.carrentalservice.model.Car;

import java.util.List;

public interface CarDao extends GenericDao<Car> {
    List<Car> findByBranchId(Long branchId);
}
