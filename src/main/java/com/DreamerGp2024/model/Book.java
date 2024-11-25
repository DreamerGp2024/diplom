package com.DreamerGp2024.model;

import java.io.Serializable;

public class Book implements Serializable {

    private String barcode;
    private String name;
    private String author;
    private double price;
    private int quantity;
    private  String descriptionbook;

    public Book(String barcode, String name, String author, double price, int quantity, String descriptionbook) {
        this.barcode = barcode;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.descriptionbook=descriptionbook;
    }

    public Book() {
        super();
    }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) {this.barcode = barcode;    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) { this.price = price; }
    public String getDescriptionBook() {
        return descriptionbook;
    }
    public void setDescriptionBook(String descriptionbook) { this.descriptionbook = descriptionbook; }
}
