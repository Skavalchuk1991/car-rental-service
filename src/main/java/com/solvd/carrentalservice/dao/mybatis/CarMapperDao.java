package com.solvd.carrentalservice.dao.mybatis;

import com.solvd.carrentalservice.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarMapperDao {
    void create(Car car);

    Optional<Car> findById(Long id);

    List<Car> findAll();

    void update(Car car);

    void delete(Long id);

    List<Car> findByBranchId(Long branchId);
}
