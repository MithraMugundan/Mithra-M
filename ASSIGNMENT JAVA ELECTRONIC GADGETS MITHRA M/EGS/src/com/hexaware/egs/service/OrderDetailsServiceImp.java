package com.hexaware.egs.service;

import com.hexaware.egs.dao.IOrderDetailsDao;
import com.hexaware.egs.dao.OrderDetailsDaoImp;
import com.hexaware.egs.entity.OrderDetails;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.PaymentFailedException;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.exception.IncompleteOrderException;

public class OrderDetailsServiceImp implements IOrderDetailsService {
    IOrderDetailsDao dao = new OrderDetailsDaoImp();

    public OrderDetailsServiceImp() {
    }

    public double calculateSubtotal(int orderDetailId) throws InvalidDataException, SqlException {
        return this.dao.calculateSubtotal(orderDetailId);
    }

    public OrderDetails getOrderDetailInfo(int orderDetailId) throws SqlException, IncompleteOrderException {
        return this.dao.getOrderDetailInfo(orderDetailId);
    }

    public int updateQuantity(int orderDetailId, int newQuantity) throws InvalidDataException, InsufficientStockException, ConcurrencyException, SqlException {
        return this.dao.updateQuantity(orderDetailId, newQuantity);
    }

    public boolean addDiscount(int orderDetailId, double discountPercent) throws InvalidDataException, SqlException, PaymentFailedException {
        return this.dao.addDiscount(orderDetailId, discountPercent);
    }

    public static boolean validateOrderDetail(OrderDetails orderDetail) {
        boolean flag = false;
        if (orderDetail != null &&
            orderDetail.getProduct() != null &&
            orderDetail.getQuantity() > 0) {
            flag = true;
        }
        return flag;
    }
}
