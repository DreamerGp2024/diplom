package com.DreamerGp2024.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private int orderID;
    private int customer;
    private String barcode;
    private double price;
    private int quantity;
    private double total;
    private int manager;
    private OrderStatus status;

    public Order(int orderID, int customer, String barcode, double price, int quantity, double total, int manager,OrderStatus status) {
        this.orderID = orderID;
        this.customer = customer;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.manager = manager;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order() {
        super();
    }

}
