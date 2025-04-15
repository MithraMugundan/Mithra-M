package com.hexaware.egs.entity;

public class OrderDetails {
   private int orderDetailId;
   private Orders order;
   private Products product;
   private int quantity;

   // Default constructor
   public OrderDetails() {
   }

   // Parameterized constructor
   public OrderDetails(int orderDetailId, Orders order, Products product, int quantity) {
      this.orderDetailId = orderDetailId;
      this.order = order;
      this.product = product;
      this.quantity = quantity;
   }

   // Getters and Setters
   public int getOrderDetailId() {
      return this.orderDetailId;
   }

   public void setOrderDetailId(int orderDetailId) {
      this.orderDetailId = orderDetailId;
   }

   public Orders getOrder() {
      return this.order;
   }

   public void setOrder(Orders order) {
      this.order = order;
   }

   public Products getProduct() {
      return this.product;
   }

   public void setProduct(Products product) {
      this.product = product;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   @Override
   public String toString() {
      return "OrderDetails [orderDetailId=" + this.orderDetailId 
            + ", order=" + this.order 
            + ", product=" + this.product 
            + ", quantity=" + this.quantity + "]";
   }
}
