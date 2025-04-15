package com.hexaware.egs.dao;

import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.entity.Orders;

import java.io.IOException;

public interface IOrdersDao {

    double calculateTotalAmount(int orderId) throws InvalidDataException, SqlException;

    Orders getOrderDetails(int orderId) throws IncompleteOrderException, SqlException;

    int updateOrderStatus(int orderId, String newStatus)
        throws InvalidDataException, SqlException, ConcurrencyException;

    int cancelOrder(int orderId)
        throws InsufficientStockException, SqlException, IOException, ConcurrencyException;

}
