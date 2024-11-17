package com.DreamerGp2024.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.DreamerGp2024.constant.ResponseCode;
import com.DreamerGp2024.constant.db.UsersDBConstants;
import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.model.User;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.UserService;
import com.DreamerGp2024.util.DBUtil;

import static com.DreamerGp2024.constant.db.UsersDBConstants.*;

public class UserServiceImpl implements UserService {

    private static final String registerUserQuery = "INSERT INTO " + UsersDBConstants.TABLE_USERS
            + "  VALUES(?,?,?,?,?,?,?,?)";

    private static final String loginUserQuery = "SELECT * FROM " + UsersDBConstants.TABLE_USERS + " WHERE "
            + UsersDBConstants.COLUMN_EMAIL + "=? AND " + UsersDBConstants.COLUMN_PASSWORD + "=?";

    private static final String getUserNameByUserIDQuery = "SELECT * FROM " + UsersDBConstants.TABLE_USERS + " WHERE "
            + COLUMN_USERID + "=?";


    private static String getRoleIDByString(String role){
        return switch (role) {
            case "3" -> "ADMIN";
            case "1" -> "MANAGER";
            default -> "CUSTOMER";
        };
    }

    @Override
    public User login(String email, String password, HttpSession session) throws StoreException {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps;
        User user = null;
        System.out.println(email);
        try {
            ps = con.prepareStatement(loginUserQuery);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setFirstName(rs.getString(COLUMN_FIRSTNAME));
                user.setLastName(rs.getString(COLUMN_LASTNAME));
                user.setPhone(rs.getString(COLUMN_PHONE));
                user.setEmail(email);
                user.setPasswordUser(password);
                user.setUserID(rs.getInt(COLUMN_USERID));
                System.out.println(getRoleIDByString(this.getRoleByUserID(user.getUserID())));
                session.setAttribute(getRoleIDByString(this.getRoleByUserID(user.getUserID())), user.getEmail());
                session.setAttribute("userID", user.getUserID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean isLoggedIn(UserRole role, HttpSession session) {
        if (role == null)
            role = UserRole.CUSTOMER;
        return session.getAttribute(role.toString()) != null;
    }

    @Override
    public boolean logout(HttpSession session) {
        session.removeAttribute(UserRole.CUSTOMER.toString());
        session.removeAttribute(UserRole.MANAGER.toString());
        session.invalidate();
        return true;
    }

    @Override
    public String register(UserRole role, User user) throws StoreException {
        String responseMessage = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            System.out.println(user);
            PreparedStatement ps = con.prepareStatement(registerUserQuery);
            Random random = new Random();
            int randomId = random.nextInt(Integer.MAX_VALUE);
            ps.setInt(1, randomId);
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordUser());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getAddress());
            int userType = UserRole.MANAGER.equals(role) ? 1 : 2;
            ps.setInt(8, userType);

            int k = ps.executeUpdate();
            if (k == 1) {
                responseMessage = ResponseCode.SUCCESS.name();
            }
        } catch (Exception e) {
            responseMessage += " : " + e.getMessage();
            if (responseMessage.contains("Duplicate"))
                responseMessage = "User already registered with this email !!";
            e.printStackTrace();
        }
        return responseMessage;
    }

    @Override
    public String getNameByUserID(int userID) throws StoreException {
        String phone = null;
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getUserNameByUserIDQuery);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                phone = rs.getString(COLUMN_PHONE);
            }
        } catch (SQLException ignored) {

        }
        return phone;
    }

    @Override
    public String getRoleByUserID(int userID) throws StoreException {
        String role = null;
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getUserNameByUserIDQuery);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                role = rs.getString(COLUMN_ROLE);
            }
        } catch (SQLException ignored) {

        }
        return role;
    }
}
