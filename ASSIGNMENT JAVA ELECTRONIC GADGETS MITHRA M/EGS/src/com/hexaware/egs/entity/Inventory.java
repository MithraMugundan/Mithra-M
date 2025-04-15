package com.hexaware.egs.entity;

import java.util.Date;

public class Inventory {

   private int inventoryId;
   private Products product;  // Composition: Each inventory is associated with a product.
   private int quantityInStock;
   private Date lastStockUpdate;

   // Default constructor
   public Inventory() {
   }

   // Parameterized constructor
   public Inventory(int inventoryId, Products product, int quantityInStock, Date lastStockUpdate) {
      this.inventoryId = inventoryId;
      this.product = product;
      this.quantityInStock = quantityInStock;
      this.lastStockUpdate = lastStockUpdate;
   }

   // Getters and Setters
   public int getInventoryId() {
      return this.inventoryId;
   }

   public void setInventoryId(int inventoryId) {
      this.inventoryId = inventoryId;
   }

   public Products getProduct() {
      return this.product;
   }

   public void setProduct(Products product) {
      this.product = product;
   }

   public int getQuantityInStock() {
      return this.quantityInStock;
   }

   public void setQuantityInStock(int quantityInStock) {
      this.quantityInStock = quantityInStock;
   }

   public Date getLastStockUpdate() {
      return this.lastStockUpdate;
   }

   public void setLastStockUpdate(Date lastStockUpdate) {
      this.lastStockUpdate = lastStockUpdate;
   }

   @Override
   public String toString() {
      return "Inventory [inventoryId=" + this.inventoryId + ", product=" + this.product.getProductName()
            + ", quantityInStock=" + this.quantityInStock + ", lastStockUpdate=" + this.lastStockUpdate + "]";
   }
}
