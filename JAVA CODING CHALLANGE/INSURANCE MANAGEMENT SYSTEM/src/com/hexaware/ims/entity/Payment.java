package com.hexaware.ims.entity;


import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private LocalDate paymentDate;
    private double paymentAmount;
    private Client client;

    // Default constructor
    public Payment() {}

    // Parameterized constructor
    public Payment(int paymentId, LocalDate paymentDate, double paymentAmount, Client client) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.client = client;
    }

    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // toString method
    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", paymentDate=" + paymentDate
                + ", paymentAmount=" + paymentAmount + ", client=" + client + "]";
    }
}
