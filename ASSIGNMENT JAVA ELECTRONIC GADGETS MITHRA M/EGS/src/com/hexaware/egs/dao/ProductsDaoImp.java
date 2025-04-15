package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.ProductNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsDaoImp implements IProductsDao {
    
    public ProductsDaoImp() {
    }

    // Method to get detailed product information
    public Products getProductDetails(int productId) throws ProductNotFoundException {
        Products product = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "select * from products where productid = ?";
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new ProductNotFoundException();
            }

            int id = rs.getInt("productid");
            String productName = rs.getString("productname");
            String description = rs.getString("description");
            double price = rs.getDouble("price");

            product = new Products(id, productName, description, price);
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return product;
    }

    // Method to update product information (e.g., price, description)
    public int updateProductInfo(Products product) {
        int count = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String update = "update products set productname=?, description=?, price=? where productid=?";
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getProductId());
            count = pstmt.executeUpdate();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return count;
    }

    // Method to check if the product is in stock
    public boolean isProductInStock(int productId) {
        boolean inStock = false;

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "select stock from products where productid = ?";
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                inStock = rs.getInt("stock") > 0;
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return inStock;
    }
}


