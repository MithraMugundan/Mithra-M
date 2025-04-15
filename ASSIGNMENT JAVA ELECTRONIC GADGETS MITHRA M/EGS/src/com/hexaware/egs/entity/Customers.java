package com.hexaware.egs.entity;

public class Customers {
   private int customerId;
   private String firstName;
   private String lastName;
   private String email;
   private String phone;
   private String address;

   // Default constructor
   public Customers() {
   }

   // Parameterized constructor
   public Customers(int customerId, String firstName, String lastName, String email, String phone, String address) {
      this.customerId = customerId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.phone = phone;
      this.address = address;
   }

   // Getters and Setters
   public int getCustomerId() {
      return this.customerId;
   }

   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return this.lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   @Override
   public String toString() {
      return "Customer [customerId=" + this.customerId + ", firstName=" + this.firstName + ", lastName=" + this.lastName
            + ", email=" + this.email + ", phone=" + this.phone + ", address=" + this.address + "]";
   }
}
