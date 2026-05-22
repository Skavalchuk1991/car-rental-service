package com.solvd.carrentalservice.dao.mybatis;

import com.solvd.carrentalservice.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchMapperDao {
    void create(Branch branch);

    Optional<Branch> findById(Long id);

    List<Branch> findAll();

    void update(Branch branch);

    void delete(Long id);
}
