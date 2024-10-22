package com.DreamerGp2024.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private int orderID;
    private int customer;
    private List<String> barcode;
    private double price;
    private List<Integer> quantity;
    private double total;
    private int manager;
    private OrderStatus status;

    public Order(int orderID, int customer, List<String> barcode, double price, List<Integer> quantity, double total, int manager,OrderStatus status) {
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

    public List<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }
}
