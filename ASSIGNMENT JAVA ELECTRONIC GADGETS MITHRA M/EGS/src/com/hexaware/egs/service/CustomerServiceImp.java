// Source code is decompiled from a .class file using FernFlower decompiler.
package com.hexaware.egs.service;

import com.hexaware.egs.dao.CustomerDaoImp;
import com.hexaware.egs.dao.ICustomerDao;
import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.exception.CustomerNotFoundException;

public class CustomerServiceImp implements ICustomerService {
   ICustomerDao dao = new CustomerDaoImp();

   public CustomerServiceImp() {
   }

   public int calculateTotalOrders(int customerId) {
      return this.dao.calculateTotalOrders(customerId);
   }

   public Customers getCustomerDetails(int customerId) throws CustomerNotFoundException {
      return this.dao.getCustomerDetails(customerId);
   }

   public int updateCustomerInfo(Customers customer) {
      return this.dao.updateCustomerInfo(customer);
   }

   public static boolean validateCustomer(Customers customer) {
      boolean flag = false;
      if (customer.getFirstName().length() > 2 &&
          customer.getEmail().contains("@") &&
          customer.getPhone().length() >= 10) {
         flag = true;
      }

      return flag;
   }
}
