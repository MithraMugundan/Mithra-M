package com.hexaware.egs.entity;

public class Products {
   private int productId;
   private String productName;
   private String description;
   private double price;

   // Default constructor
   public Products() {
   }

   // Parameterized constructor
   public Products(int productId, String productName, String description, double price) {
      this.productId = productId;
      this.productName = productName;
      this.description = description;
      this.price = price;
   }

   // Getters and Setters
   public int getProductId() {
      return this.productId;
   }

   public void setProductId(int productId) {
      this.productId = productId;
   }

   public String getProductName() {
      return this.productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public double getPrice() {
      return this.price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   @Override
   public String toString() {
      return "Product [productId=" + this.productId + ", productName=" + this.productName +
             ", description=" + this.description + ", price=" + this.price + "]";
   }
}
