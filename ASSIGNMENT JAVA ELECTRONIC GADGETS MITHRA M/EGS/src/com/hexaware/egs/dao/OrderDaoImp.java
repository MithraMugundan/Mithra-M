package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Orders;
import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.PaymentFailedException;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.AuthenticationException;
import com.hexaware.egs.exception.AuthorizationException;
import com.hexaware.egs.exception.IncompleteOrderException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.dao.DBUtil;
import com.hexaware.egs.dao.IOrdersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderDaoImp implements IOrdersDao {

    // Method to calculate the total amount of the order
    public double calculateTotalAmount(int orderId) throws InvalidDataException {
        double totalAmount = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT SUM(quantity * price) AS total FROM orderdetails WHERE orderid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalAmount = rs.getDouble("total");
            } else {
                throw new InvalidDataException("Order ID not found or has no items.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }

    // Method to get order details
    public Orders getOrderDetails(int orderId) throws IncompleteOrderException {
        Orders order = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT * FROM orders WHERE orderid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new IncompleteOrderException("Order details are missing or incomplete.");
            }

            int id = rs.getInt("orderid");
            int customerId = rs.getInt("customerid");
            LocalDateTime orderDate = rs.getTimestamp("orderdate").toLocalDateTime();
            double totalAmount = rs.getDouble("totalamount");

            Customers customer = new Customers(); // You might want to populate this if needed
            customer.setCustomerId(customerId);

            order = new Orders(id, customer, orderDate, totalAmount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    // Method to update the status of the order
    public int updateOrderStatus(int orderId, String newStatus) throws InvalidDataException {
        int rowsUpdated = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "UPDATE orders SET status = ? WHERE orderid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);
            rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new InvalidDataException("Invalid order ID or status update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsUpdated;
    }

    // Method to cancel the order and adjust stock
    public int cancelOrder(int orderId) throws InsufficientStockException {
        int result = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            conn.setAutoCommit(false);

            // Revert stock
            String updateStock = "UPDATE products p JOIN orderdetails oi ON p.productid = oi.productid "
                               + "SET p.stock = p.stock + oi.quantity WHERE oi.orderid = ?";
            PreparedStatement stockStmt = conn.prepareStatement(updateStock);
            stockStmt.setInt(1, orderId);
            stockStmt.executeUpdate();

            // Update order status
            String cancelOrder = "UPDATE orders SET status = 'Cancelled' WHERE orderid = ?";
            PreparedStatement cancelStmt = conn.prepareStatement(cancelOrder);
            cancelStmt.setInt(1, orderId);
            result = cancelStmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InsufficientStockException("Failed to update stock or cancel the order.");
        }

        return result;
    }


}
