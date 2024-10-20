package com.DreamerGp2024.service.impl;

import com.DreamerGp2024.constant.db.OrdersDBConstants;
import com.DreamerGp2024.model.Order;
import com.DreamerGp2024.model.OrderStatus;
import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.service.OrderService;
import com.DreamerGp2024.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static OrderStatus getStatusByString(String status) {
        switch (status) {
            case "NEW":
                return (OrderStatus.NEW);
            case "PROCESS":
                return (OrderStatus.PROCESS);
            default:
                return (OrderStatus.COMPLETED);
        }
    }

    private static final String getOrderByIdQuery = "SELECT * FROM " + OrdersDBConstants.TABLE_ORDERS
            + " WHERE " + OrdersDBConstants.COLUMN_ORDERID + " = ?";

    private static final String getOrdersQuery = "SELECT * FROM " + OrdersDBConstants.TABLE_ORDERS;

    @Override
    public Order getOrderById(String orderID) throws StoreException {
        Order order = null;
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getOrderByIdQuery);
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                order = new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getInt(7),
                        getStatusByString(rs.getString(8))
                );
            }
        } catch (SQLException ignored) {

        }
        return order;
    }

    @Override
    public List<Order> getOrders() throws StoreException {
        List<Order> orders = new ArrayList<>();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getOrdersQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getInt(7),
                        getStatusByString(rs.getString(8))
                ));
            }
        } catch (SQLException ignored) {

        }
        return orders;
    }

    @Override
    public String finishOrderById(String orderID) throws StoreException {
        return "";
    }

    @Override
    public String addOrder(Order order) throws StoreException {
        return "";
    }
}
