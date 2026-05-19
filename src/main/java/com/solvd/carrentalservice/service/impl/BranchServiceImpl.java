package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.BranchDao;
import com.solvd.carrentalservice.dao.impl.BranchDaoImpl;
import com.solvd.carrentalservice.model.Branch;
import com.solvd.carrentalservice.service.BranchService;

import java.util.List;
import java.util.Optional;

public class BranchServiceImpl implements BranchService {
    private final BranchDao branchDao = new BranchDaoImpl();

    @Override
    public void create(Branch branch) {
        branchDao.create(branch);
    }

    @Override
    public Optional<Branch> findById(Long id) {
        return branchDao.findById(id);
    }

    @Override
    public List<Branch> findAll() {
        return branchDao.findAll();
    }

    @Override
    public void update(Branch branch) {
        branchDao.update(branch);
    }

    @Override
    public void delete(Long id) {
        branchDao.delete(id);
    }
}
