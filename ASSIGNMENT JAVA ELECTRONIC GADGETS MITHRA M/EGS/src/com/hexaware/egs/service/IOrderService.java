package com.hexaware.egs.service;

import java.io.IOException;

import com.hexaware.egs.entity.Orders;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.SqlException;

public interface IOrderService {

    double calculateTotalAmount(int orderId) throws InvalidDataException, SqlException;

    Orders getOrderDetails(int orderId) throws IncompleteOrderException, SqlException;

    int updateOrderStatus(int orderId, String newStatus) throws InvalidDataException, SqlException, ConcurrencyException;

    int cancelOrder(int orderId) throws InsufficientStockException, SqlException, IOException, ConcurrencyException;
}
