package com.solvd.carrentalservice.factory;

import com.solvd.carrentalservice.dao.CarDao;
import com.solvd.carrentalservice.dao.CustomerDao;
import com.solvd.carrentalservice.dao.RentalDao;
import com.solvd.carrentalservice.dao.impl.CarDaoImpl;
import com.solvd.carrentalservice.dao.impl.CustomerDaoImpl;
import com.solvd.carrentalservice.dao.impl.RentalDaoImpl;

public class JdbcDaoFactory implements DaoAbstractFactory {
    @Override
    public CarDao createCarDao() {
        return new CarDaoImpl();
    }

    @Override
    public CustomerDao createCustomerDao() {
        return new CustomerDaoImpl();
    }

    @Override
    public RentalDao createRentalDao() {
        return new RentalDaoImpl();
    }
}
