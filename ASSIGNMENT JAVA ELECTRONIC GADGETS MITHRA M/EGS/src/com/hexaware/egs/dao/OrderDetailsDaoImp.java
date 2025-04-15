package com.hexaware.egs.dao;

import com.hexaware.egs.entity.OrderDetails;
import com.hexaware.egs.entity.Orders;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.PaymentFailedException;
import com.hexaware.egs.exception.ConcurrencyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsDaoImp implements IOrderDetailsDao {

    // Method to calculate sub total for this order detail
    public double calculateSubtotal(int orderDetailId) throws InvalidDataException {
        double subtotal = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT quantity * price AS subtotal FROM orderdetails oi JOIN products p ON oi.productid = p.productid WHERE orderdetailid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderDetailId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                subtotal = rs.getDouble("subtotal");
            } else {
                throw new InvalidDataException("Invalid Order Detail ID or data missing.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtotal;
    }

    // Method to get order detail information
    public OrderDetails getOrderDetailInfo(int orderDetailId) throws IncompleteOrderException {
        OrderDetails orderDetail = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT * FROM orderdetails WHERE orderdetailid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderDetailId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new IncompleteOrderException("Order detail is missing or incomplete.");
            }

            int detailId = rs.getInt("orderdetailid");
            int orderId = rs.getInt("orderid");
            int productId = rs.getInt("productid");
            int quantity = rs.getInt("quantity");

            Orders order = new Orders();
            order.setOrderId(orderId);

            Products product = new Products();
            product.setProductId(productId);

            orderDetail = new OrderDetails(detailId, order, product, quantity);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetail;
    }

    // Method to update the quantity in an order detail
    public int updateQuantity(int orderDetailId, int newQuantity) throws InvalidDataException, InsufficientStockException, ConcurrencyException,SqlException {
        int rowsUpdated = 0;

        try {
            Connection conn = DBUtil.getDBConnection();

            // Optional: Check if stock is available before updating
            String stockCheck = "SELECT p.stock FROM products p JOIN orderdetails oi ON p.productid = oi.productid WHERE oi.orderdetailid = ?";
            PreparedStatement checkStmt = conn.prepareStatement(stockCheck);
            checkStmt.setInt(1, orderDetailId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int availableStock = rs.getInt("stock");
                if (newQuantity > availableStock) {
                    throw new InsufficientStockException("Not enough stock to update quantity.");
                }
            }

            String updateQuery = "UPDATE orderdetails SET quantity = ? WHERE orderdetailid = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, orderDetailId);
            rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new InvalidDataException("Order detail not found or quantity not updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsUpdated;
    }

    // Method to apply discount to the order detail
    public boolean addDiscount(int orderDetailId, double discountPercent) throws InvalidDataException, PaymentFailedException {
        boolean success = false;

        try {
            if (discountPercent < 0 || discountPercent > 100) {
                throw new InvalidDataException("Invalid discount percentage.");
            }

            Connection conn = DBUtil.getDBConnection();
            String updateQuery = "UPDATE orderdetails SET discount = ? WHERE orderdetailid = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setDouble(1, discountPercent);
            pstmt.setInt(2, orderDetailId);
            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                throw new PaymentFailedException("Failed to apply discount to the order detail.");
            }

            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
