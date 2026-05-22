package com.solvd.carrentalservice.service.impl;

import com.solvd.carrentalservice.dao.mybatis.CarMapperDao;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.service.CarService;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {

    @Override
    public void create(Car car) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CarMapperDao.class).create(car);
            session.commit();
        }
    }

    @Override
    public Optional<Car> findById(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CarMapperDao.class).findById(id);
        }
    }

    @Override
    public List<Car> findAll() {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CarMapperDao.class).findAll();
        }
    }

    @Override
    public void update(Car car) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CarMapperDao.class).update(car);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            session.getMapper(CarMapperDao.class).delete(id);
            session.commit();
        }
    }

    @Override
    public List<Car> findByBranchId(Long branchId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
            return session.getMapper(CarMapperDao.class).findByBranchId(branchId);
        }
    }
}
