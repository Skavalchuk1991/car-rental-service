package com.solvd.carrentalservice.factory;

import com.solvd.carrentalservice.dao.CarDao;
import com.solvd.carrentalservice.dao.CustomerDao;
import com.solvd.carrentalservice.dao.RentalDao;

public interface DaoAbstractFactory {
    CarDao createCarDao();

    CustomerDao createCustomerDao();

    RentalDao createRentalDao();
}
