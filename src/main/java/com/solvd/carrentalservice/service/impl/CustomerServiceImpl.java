package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.CustomerMapperDao;
import com.solvd.carrentalservice.model.Customer;
import com.solvd.carrentalservice.service.CustomerService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public void create(Customer customer) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CustomerMapperDao.class).create(customer);
            session.commit();
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CustomerMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Customer> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CustomerMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Customer customer) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CustomerMapperDao.class).update(customer);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CustomerMapperDao.class).delete(id);
            session.commit();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CustomerMapperDao.class).findByEmail(email);
        }
    }
}
