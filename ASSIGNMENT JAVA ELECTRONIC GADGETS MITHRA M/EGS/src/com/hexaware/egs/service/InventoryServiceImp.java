package com.hexaware.egs.service;

import com.hexaware.egs.dao.IInventoryDao;
import com.hexaware.egs.dao.InventoryDaoImp;
import com.hexaware.egs.entity.Inventory;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.*;

import java.util.List;

public class InventoryServiceImp implements IInventoryService {

    IInventoryDao dao = new InventoryDaoImp();

    public InventoryServiceImp() {
    }

    public Products getProduct(int inventoryId) throws InvalidDataException, SqlException {
        return dao.getProduct(inventoryId);
    }

    public int getQuantityInStock(int inventoryId) throws InvalidDataException, SqlException {
        return dao.getQuantityInStock(inventoryId);
    }

    public int addToInventory(int inventoryId, int quantity) throws InvalidDataException, SqlException, ConcurrencyException {
        return dao.addToInventory(inventoryId, quantity);
    }

    public int removeFromInventory(int inventoryId, int quantity) throws InsufficientStockException, SqlException, ConcurrencyException {
        return dao.removeFromInventory(inventoryId, quantity);
    }

    public int updateProductInventory(int inventoryId, int newQuantity) throws InvalidDataException, SqlException, ConcurrencyException {
        return dao.updateStockQuantity(inventoryId, newQuantity);
    }

    public boolean isProductAvailable(int inventoryId, int quantityToCheck) throws SqlException {
        return dao.isProductAvailable(inventoryId, quantityToCheck);
    }

    public double getInventoryValue(int inventoryId) throws SqlException {
        return dao.getInventoryValue(inventoryId);
    }

    public List<Inventory> listLowStockProducts(int threshold) throws SqlException {
        return dao.listLowStockProducts(threshold);
    }

    public List<Inventory> listOutOfStockProducts() throws SqlException {
        return dao.listOutOfStockProducts();
    }

    public List<Inventory> listAllProducts() throws SqlException {
        return dao.listAllProducts();
    }

    public static boolean validateInventory(Inventory inv) {
        boolean flag = false;
        if (inv != null && inv.getProduct() != null && inv.getQuantityInStock() >= 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public int updateStockQuantity(int inventoryId, int newQuantity) throws InvalidDataException, SqlException, ConcurrencyException {
        return dao.updateStockQuantity(inventoryId, newQuantity);
    }
}
