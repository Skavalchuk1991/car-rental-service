package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.BranchMapperDao;
import com.solvd.carrentalservice.model.Branch;
import com.solvd.carrentalservice.service.BranchService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class BranchServiceImpl implements BranchService {

    @Override
    public void create(Branch branch) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(BranchMapperDao.class).create(branch);
            session.commit();
        }
    }

    @Override
    public Optional<Branch> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(BranchMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Branch> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(BranchMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Branch branch) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(BranchMapperDao.class).update(branch);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(BranchMapperDao.class).delete(id);
            session.commit();
        }
    }
}
