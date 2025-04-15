package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.exception.CustomerNotFoundException;

public interface ICustomerDao {

    int calculateTotalOrders(int customerId);

    Customers getCustomerDetails(int customerId) throws CustomerNotFoundException;

    int updateCustomerInfo(Customers customer);
}
