package com.hexaware.egs.service;

import com.hexaware.egs.entity.OrderDetails;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.PaymentFailedException;
import com.hexaware.egs.exception.SqlException;

public interface IOrderDetailsService {

    double calculateSubtotal(int orderDetailId) throws InvalidDataException, SqlException;

    OrderDetails getOrderDetailInfo(int orderDetailId) throws IncompleteOrderException, SqlException;

    int updateQuantity(int orderDetailId, int newQuantity) throws InvalidDataException, InsufficientStockException, SqlException, ConcurrencyException;

    boolean addDiscount(int orderDetailId, double discountPercent) throws InvalidDataException, SqlException, PaymentFailedException;
}
