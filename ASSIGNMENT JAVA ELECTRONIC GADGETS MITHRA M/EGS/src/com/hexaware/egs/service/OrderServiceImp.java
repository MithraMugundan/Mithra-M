package com.hexaware.egs.service;

import java.io.IOException;

import com.hexaware.egs.dao.IOrdersDao;
import com.hexaware.egs.dao.OrderDaoImp;
import com.hexaware.egs.entity.Orders;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.InsufficientStockException;

public class OrderServiceImp implements IOrderService {
    IOrdersDao dao = new OrderDaoImp();

    public OrderServiceImp() {
    }

    public double calculateTotalAmount(int orderId) throws InvalidDataException, SqlException {
        return this.dao.calculateTotalAmount(orderId);
    }

    public Orders getOrderDetails(int orderId) throws IncompleteOrderException, SqlException {
        return this.dao.getOrderDetails(orderId);
    }

    public int updateOrderStatus(int orderId, String newStatus) throws InvalidDataException, SqlException, ConcurrencyException {
        return this.dao.updateOrderStatus(orderId, newStatus);
    }

    public int cancelOrder(int orderId) throws InsufficientStockException, SqlException, IOException, ConcurrencyException {
        return this.dao.cancelOrder(orderId);
    }

    public static boolean validateOrder(Orders order) {
        boolean flag = false;
        if (order != null &&
            order.getCustomer() != null &&
            order.getTotalAmount() >= 0) {
            flag = true;
        }

        return flag;
    }
}
