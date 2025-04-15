package com.hexaware.egs.entity;

import java.time.LocalDateTime;

public class Orders {
   private int orderId;
   private Customers customer;
   private LocalDateTime orderDate;
   private double totalAmount;

   // Default constructor
   public Orders() {
   }

   // Parameterized constructor
   public Orders(int orderId, Customers customer, LocalDateTime orderDate, double totalAmount) {
      this.orderId = orderId;
      this.customer = customer;
      this.orderDate = orderDate;
      this.totalAmount = totalAmount;
   }

   // Getters and Setters
   public int getOrderId() {
      return this.orderId;
   }

   public void setOrderId(int orderId) {
      this.orderId = orderId;
   }

   public Customers getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customers customer) {
      this.customer = customer;
   }

   public LocalDateTime getOrderDate() {
      return this.orderDate;
   }

   public void setOrderDate(LocalDateTime orderDate) {
      this.orderDate = orderDate;
   }

   public double getTotalAmount() {
      return this.totalAmount;
   }

   public void setTotalAmount(double totalAmount) {
      this.totalAmount = totalAmount;
   }

   @Override
   public String toString() {
      return "Orders [orderId=" + this.orderId 
            + ", customer=" + this.customer 
            + ", orderDate=" + this.orderDate 
            + ", totalAmount=" + this.totalAmount + "]";
   }
}
