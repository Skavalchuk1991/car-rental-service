package com.solvd.carrentalservice.factory;

import com.solvd.carrentalservice.dao.CarDao;
import com.solvd.carrentalservice.dao.impl.CarDaoImpl;
import com.solvd.carrentalservice.dao.mybatis.CarMapperDao;
import com.solvd.carrentalservice.util.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CarDaoFactory {

    private static final Logger LOGGER = LogManager.getLogger(CarDaoFactory.class);

    public static CarDao createCarDao(DaoType type) {
        LOGGER.info("Creating CarDao with type: {}", type);
        return switch (type) {
            case JDBC -> new CarDaoImpl();
            case MYBATIS -> new MyBatisCarDaoAdapter();
        };
    }

    private static class MyBatisCarDaoAdapter implements CarDao {
        @Override
        public void create(com.solvd.carrentalservice.model.Car car) {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                s.getMapper(CarMapperDao.class).create(car);
                s.commit();
            }
        }

        @Override
        public Optional<com.solvd.carrentalservice.model.Car> findById(Long id) {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                return s.getMapper(CarMapperDao.class).findById(id);
            }
        }

        @Override
        public List<com.solvd.carrentalservice.model.Car> findAll() {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                return s.getMapper(CarMapperDao.class).findAll();
            }
        }

        @Override
        public void update(com.solvd.carrentalservice.model.Car car) {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                s.getMapper(CarMapperDao.class).update(car);
                s.commit();
            }
        }

        @Override
        public void delete(Long id) {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                s.getMapper(CarMapperDao.class).delete(id);
                s.commit();
            }
        }

        @Override
        public List<com.solvd.carrentalservice.model.Car> findByBranchId(Long branchId) {
            try (SqlSession s = MyBatisSessionFactory.getSqlSessionFactory().openSession()) {
                return s.getMapper(CarMapperDao.class).findByBranchId(branchId);
            }
        }
    }
}
