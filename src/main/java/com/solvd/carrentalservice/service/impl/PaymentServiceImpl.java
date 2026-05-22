package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.PaymentMapperDao;
import com.solvd.carrentalservice.model.Payment;
import com.solvd.carrentalservice.service.PaymentService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public void create(Payment payment) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(PaymentMapperDao.class).create(payment);
            session.commit();
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(PaymentMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Payment> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(PaymentMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Payment payment) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(PaymentMapperDao.class).update(payment);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(PaymentMapperDao.class).delete(id);
            session.commit();
        }
    }

    @Override
    public Optional<Payment> findByRentalId(Long rentalId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(PaymentMapperDao.class).findByRentalId(rentalId);
        }
    }
}
