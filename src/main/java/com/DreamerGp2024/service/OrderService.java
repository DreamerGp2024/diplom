package com.DreamerGp2024.service;

import com.DreamerGp2024.model.Order;
import com.DreamerGp2024.model.OrderStatus;
import com.DreamerGp2024.model.StoreException;

import java.util.List;

public interface OrderService {
    public Order getOrderById(String orderID) throws StoreException;

    public List<Order> getOrders() throws StoreException;

    public String pushOrderStatusByID(String orderID) throws StoreException;

    public String attachManagerToOrderByID(String orderID, int mngr) throws StoreException;

    public String addOrder(Order order) throws StoreException;
}
