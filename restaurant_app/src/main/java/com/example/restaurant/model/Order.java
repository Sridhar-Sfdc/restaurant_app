package com.example.restaurant.model;

import java.util.concurrent.atomic.AtomicLong;

public class Order {

    private static final AtomicLong idCounter = new AtomicLong();

    private Long id;
    private String customerName;
    private String phoneNumber;
    private String orderDetails;
    private OrderStatus status;

    public Order(String customerName, String phoneNumber, String orderDetails, OrderStatus status) {
        this.id = idCounter.incrementAndGet();  // Automatically generated unique ID
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.orderDetails = orderDetails;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
