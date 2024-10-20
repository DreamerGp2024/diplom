package com.DreamerGp2024.service;

import com.DreamerGp2024.model.Book;
import com.DreamerGp2024.model.Order;
import com.DreamerGp2024.model.OrderStatus;
import com.DreamerGp2024.model.StoreException;

import java.util.List;

public interface OrderService {
    public Order getOrderById(String orderID) throws StoreException;

    public List<Order> getOrders() throws StoreException;

//    public List<Book> getBooksByCommaSeperatedBookIds(String commaSeperatedBookIds) throws StoreException;

    public String changeOrderStatusByID(String orderID, OrderStatus status) throws StoreException;

    public String addOrder(Order order) throws StoreException;

//    public String updateBook(Book book) throws StoreException;
}
