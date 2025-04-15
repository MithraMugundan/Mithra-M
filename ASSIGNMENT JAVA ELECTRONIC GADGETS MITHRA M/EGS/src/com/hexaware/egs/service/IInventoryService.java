package com.hexaware.egs.service;

import java.util.List;

import com.hexaware.egs.entity.Inventory;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.ConcurrencyException;
import com.hexaware.egs.exception.InsufficientStockException;
import com.hexaware.egs.exception.InvalidDataException;
import com.hexaware.egs.exception.SqlException;

public interface IInventoryService {

    Products getProduct(int inventoryId) throws InvalidDataException, SqlException;

    int getQuantityInStock(int inventoryId) throws InvalidDataException, SqlException;

    int addToInventory(int inventoryId, int quantity) throws InvalidDataException, SqlException, ConcurrencyException;

    int removeFromInventory(int inventoryId, int quantity) throws InsufficientStockException, SqlException, ConcurrencyException;

    int updateStockQuantity(int inventoryId, int newQuantity) throws InvalidDataException, SqlException, ConcurrencyException;

    boolean isProductAvailable(int inventoryId, int quantityToCheck) throws SqlException;

    double getInventoryValue(int inventoryId) throws SqlException;

    List<Inventory> listLowStockProducts(int threshold) throws SqlException;

    List<Inventory> listOutOfStockProducts() throws SqlException;

    List<Inventory> listAllProducts() throws SqlException;
}
