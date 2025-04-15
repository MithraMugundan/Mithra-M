package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.exception.CustomerNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImp implements ICustomerDao {
    public CustomerDaoImp() {
    }

    public int calculateTotalOrders(int customerId) {
        int totalOrders = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "select count(*) from orders where customerid = ?";
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalOrders = rs.getInt(1);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return totalOrders;
    }

    public Customers getCustomerDetails(int customerId) throws CustomerNotFoundException {
        Customers customer = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "select * from customers where customerid = ?";
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new CustomerNotFoundException();
            }

            int id = rs.getInt("customerid");
            String name = rs.getString("name");
            String lname = rs.getString("lname");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");

            customer = new Customers(id, name, lname, email, phone, address);
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return customer;
    }

    public int updateCustomerInfo(Customers customer) {
        int count = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String update = "update customers set name=?, lname=?, email = ?, phone = ?, address = ? where customerid = ?";
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setInt(6, customer.getCustomerId());
            count = pstmt.executeUpdate();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return count;
    }
}
