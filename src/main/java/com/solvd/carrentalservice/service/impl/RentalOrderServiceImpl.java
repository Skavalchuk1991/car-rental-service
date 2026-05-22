package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.RentalMapperDao;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.service.RentalOrderService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class RentalOrderServiceImpl implements RentalOrderService {

    @Override
    public void create(Rental rental) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(RentalMapperDao.class).create(rental);
            session.commit();
        }
    }

    @Override
    public Optional<Rental> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(RentalMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Rental> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(RentalMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Rental rental) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(RentalMapperDao.class).update(rental);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(RentalMapperDao.class).delete(id);
            session.commit();
        }
    }

    @Override
    public List<Rental> findByCustomerId(Long customerId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(RentalMapperDao.class).findByCustomerId(customerId);
        }
    }

    @Override
    public List<Rental> findAllWithDetails() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(RentalMapperDao.class).findAllWithDetails();
        }
    }
}
