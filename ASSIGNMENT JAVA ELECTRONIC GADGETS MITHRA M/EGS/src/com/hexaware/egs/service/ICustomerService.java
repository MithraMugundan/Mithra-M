package com.hexaware.egs.service;

import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.exception.CustomerNotFoundException;

public interface ICustomerService {

    int calculateTotalOrders(int customerId);

    Customers getCustomerDetails(int customerId) throws CustomerNotFoundException;

    int updateCustomerInfo(Customers customer);
}
