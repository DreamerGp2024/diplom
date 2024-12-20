package com.DreamerGp2024.service.impl;

import com.DreamerGp2024.constant.ResponseCode;
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
import java.util.Objects;

import static com.DreamerGp2024.constant.db.OrdersDBConstants.*;


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

    private static final String getOrderByIdQuery = "SELECT * FROM " + TABLE_ORDERS
            + " WHERE " + COLUMN_ORDERID + " = ?";

    private static final String getOrdersQuery = "SELECT * FROM " + TABLE_ORDERS;

    private static final String addOrdersQuery = "INSERT INTO " + TABLE_ORDERS + "  VALUES(?,?,?,?,?,?,?,?)";

    private static final String updateOrderStatusByIdQuery = "UPDATE " + TABLE_ORDERS + " SET "
            + COLUMN_STATUS + "=? "
            + "  WHERE " + COLUMN_ORDERID
            + "=?";

    private static final String updateOrderManagerByIdQuery = "UPDATE " + TABLE_ORDERS + " SET "
            + COLUMN_MANAGER + "=? "
            + "  WHERE " + COLUMN_ORDERID
            + "=?";

    @Override
    public Order getOrderById(String orderID) throws StoreException {
        Order order = null;
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getOrderByIdQuery);
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add(rs.getString(3));
                ArrayList<Double> list1 = new ArrayList<>();
                list1.add(rs.getDouble(4));
                ArrayList<Integer> list2 = new ArrayList<>();
                list2.add(rs.getInt(5));
                order = new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        list,
                        list1,
                        list2,
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
                ArrayList<String> list = new ArrayList<>();
                list.add(rs.getString(3));
                ArrayList<Double> list1 = new ArrayList<>();
                list1.add(rs.getDouble(4));
                ArrayList<Integer> list2 = new ArrayList<>();
                list2.add(rs.getInt(5));

                orders.add(new Order(rs.getInt(1), rs.getInt(2), list,  list1, list2, rs.getDouble(6), rs.getInt(7), getStatusByString(rs.getString(8))
                ));
            }
        } catch (SQLException ignored) {

        }
        return orders;
    }

    private OrderStatus pushStatus(OrderStatus status){
        if (Objects.requireNonNull(status) == OrderStatus.NEW) {
            return OrderStatus.PROCESS;
        }
        return OrderStatus.COMPLETED;
    }
    @Override
    public String pushOrderStatusByID(String orderID) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            Order order = getOrderById(orderID);

            PreparedStatement ps = con.prepareStatement(updateOrderStatusByIdQuery);
            ps.setString(1, pushStatus(order.getStatus()).toString());
            ps.setString(2, orderID);
            ps.executeUpdate();
            responseCode = ResponseCode.SUCCESS.name();
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }

    @Override
    public String attachManagerToOrderByID(String orderID, int mngr) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(updateOrderManagerByIdQuery);
            ps.setInt(1, mngr);
            ps.setString(2, orderID);
            ps.executeUpdate();
            responseCode = ResponseCode.SUCCESS.name();
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }

    @Override
    public String addOrder(Order order) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(addOrdersQuery);
            ps.setInt(1, order.getOrderID());
            ps.setInt(2, order.getCustomer());
            ps.setString(3, order.getBarcode().get(0));
            ps.setDouble(4, order.getPrice().get(0));
            ps.setInt(5, order.getQuantity().get(0));
            ps.setDouble(6, order.getPrice().get(0)*order.getQuantity().get(0));
            ps.setInt(7, order.getManager());
            ps.setString(8, order.getStatus().name());
            if (ps.executeUpdate() == 1) {
                responseCode = ResponseCode.SUCCESS.name();
            }
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }
}
