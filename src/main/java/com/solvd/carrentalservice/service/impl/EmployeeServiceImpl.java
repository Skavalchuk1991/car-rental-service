package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.EmployeeMapperDao;
import com.solvd.carrentalservice.model.Employee;
import com.solvd.carrentalservice.service.EmployeeService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public void create(Employee employee) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(EmployeeMapperDao.class).create(employee);
            session.commit();
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(EmployeeMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Employee> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(EmployeeMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Employee employee) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(EmployeeMapperDao.class).update(employee);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(EmployeeMapperDao.class).delete(id);
            session.commit();
        }
    }

    @Override
    public List<Employee> findByBranchId(Long branchId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(EmployeeMapperDao.class).findByBranchId(branchId);
        }
    }
}
