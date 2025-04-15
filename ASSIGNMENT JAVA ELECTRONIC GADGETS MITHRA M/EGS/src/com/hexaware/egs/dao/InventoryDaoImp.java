package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Inventory;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.SqlException;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.ConcurrencyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImp implements IInventoryDao {

    // Method to retrieve the product associated with this inventory item
    public Products getProduct(int inventoryId) throws SqlException, InvalidDataException {
        Products product = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT p.* FROM inventory i JOIN products p ON i.productId = p.productId WHERE i.inventoryId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("Description");
                double price = rs.getDouble("price");

                product = new Products(productId, productName, productDescription, price);
            } else {
                throw new InvalidDataException("Product not found for the given Inventory ID.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error fetching product details: " + e.getMessage());
        }

        return product;
    }

    // Method to get the current quantity of the product in stock
    public int getQuantityInStock(int inventoryId) throws SqlException, InvalidDataException {
        int quantityInStock = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT quantityInStock FROM inventory WHERE inventoryId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                quantityInStock = rs.getInt("quantityInStock");
            } else {
                throw new InvalidDataException("Inventory not found for the given ID.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error fetching inventory quantity: " + e.getMessage());
        }

        return quantityInStock;
    }

    // Method to add a specified quantity of the product to the inventory
    public int addToInventory(int inventoryId, int quantity) throws SqlException, InvalidDataException, ConcurrencyException {
        int rowsUpdated = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String checkQuery = "SELECT quantityInStock FROM inventory WHERE inventoryId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, inventoryId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantityInStock");
                int newQuantity = currentQuantity + quantity;

                String updateQuery = "UPDATE inventory SET quantityInStock = ?, lastStockUpdate = NOW() WHERE inventoryId = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, inventoryId);
                rowsUpdated = updateStmt.executeUpdate();
            } else {
                throw new InvalidDataException("Inventory not found for the given ID.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error adding to inventory: " + e.getMessage());
        }

        return rowsUpdated;
    }

    // Method to remove a specified quantity of the product from the inventory
    public int removeFromInventory(int inventoryId, int quantity) throws SqlException, InsufficientStockException, ConcurrencyException {
        int rowsUpdated = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String checkQuery = "SELECT quantityInStock FROM inventory WHERE inventoryId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, inventoryId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantityInStock");

                if (quantity > currentQuantity) {
                    throw new InsufficientStockException("Not enough stock to remove.");
                }

                int newQuantity = currentQuantity - quantity;

                String updateQuery = "UPDATE inventory SET quantityInStock = ?, lastStockUpdate = NOW() WHERE inventoryId = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, inventoryId);
                rowsUpdated = updateStmt.executeUpdate();
            } else {
                throw new SqlException("Inventory not found for the given ID.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error removing from inventory: " + e.getMessage());
        }

        return rowsUpdated;
    }

    // Method to update the stock quantity to a new value
    public int updateStockQuantity(int inventoryId, int newQuantity) throws SqlException, InvalidDataException, ConcurrencyException {
        int rowsUpdated = 0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "UPDATE inventory SET quantityInStock = ?, lastStockUpdate = NOW() WHERE inventoryId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, inventoryId);
            rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new InvalidDataException("Inventory not found for the given ID.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error updating inventory stock: " + e.getMessage());
        }

        return rowsUpdated;
    }

    // Method to check if a specified quantity of the product is available in the inventory
    public boolean isProductAvailable(int inventoryId, int quantityToCheck) throws SqlException {
        boolean isAvailable = false;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT quantityInStock FROM inventory WHERE inventoryId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int quantityInStock = rs.getInt("quantityInStock");
                isAvailable = quantityInStock >= quantityToCheck;
            }
        } catch (SQLException e) {
            throw new SqlException("Error checking inventory stock: " + e.getMessage());
        }

        return isAvailable;
    }

    // Method to calculate the total value of the products in the inventory
    public double getInventoryValue(int inventoryId) throws SqlException {
        double inventoryValue = 0.0;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT i.quantityInStock, p.price FROM inventory i JOIN products p ON i.productId = p.productId WHERE i.inventoryId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int quantityInStock = rs.getInt("quantityInStock");
                double price = rs.getDouble("price");
                inventoryValue = quantityInStock * price;
            } else {
                throw new SqlException("Error fetching inventory value.");
            }
        } catch (SQLException e) {
            throw new SqlException("Error calculating inventory value: " + e.getMessage());
        }

        return inventoryValue;
    }

    // Method to list products with quantities below a specified threshold, indicating low stock
    public List<Inventory> listLowStockProducts(int threshold) throws SqlException {
        List<Inventory> lowStockProducts = new ArrayList<>();

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT i.inventoryId, i.quantityInStock, p.productId, p.productName " +
                           "FROM inventory i JOIN products p ON i.productId = p.productId " +
                           "WHERE i.quantityInStock < ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int inventoryId = rs.getInt("inventoryId");
                int quantityInStock = rs.getInt("quantityInStock");
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");

                Products product = new Products(productId, productName, productName, productId);
                Inventory inventory = new Inventory(inventoryId, product, quantityInStock, null);
                lowStockProducts.add(inventory);
            }
        } catch (SQLException e) {
            throw new SqlException("Error listing low stock products: " + e.getMessage());
        }

        return lowStockProducts;
    }

    // Method to list products that are out of stock
    public List<Inventory> listOutOfStockProducts() throws SqlException {
        List<Inventory> outOfStockProducts = new ArrayList<>();

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT i.inventoryId, p.productId, p.productName " +
                           "FROM inventory i JOIN products p ON i.productId = p.productId " +
                           "WHERE i.quantityInStock = 0";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int inventoryId = rs.getInt("inventoryId");
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");

                Products product = new Products(productId, productName, productName, productId);
                Inventory inventory = new Inventory(inventoryId, product, 0, null);
                outOfStockProducts.add(inventory);
            }
        } catch (SQLException e) {
            throw new SqlException("Error listing out of stock products: " + e.getMessage());
        }

        return outOfStockProducts;
    }

    // Method to list all products in the inventory, along with their quantities
    public List<Inventory> listAllProducts() throws SqlException {
        List<Inventory> allProducts = new ArrayList<>();

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT i.inventoryId, i.quantityInStock, p.productId, p.productName " +
                           "FROM inventory i JOIN products p ON i.productId = p.productId";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int inventoryId = rs.getInt("inventoryId");
                int quantityInStock = rs.getInt("quantityInStock");
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");

                Products product = new Products(productId, productName, productName, productId);
                Inventory inventory = new Inventory(inventoryId, product, quantityInStock, null);
                allProducts.add(inventory);
            }
        } catch (SQLException e) {
            throw new SqlException("Error listing all products: " + e.getMessage());
        }

        return allProducts;
    }
}
