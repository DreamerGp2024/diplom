package com.DreamerGp2024.service;

import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.model.User;
import com.DreamerGp2024.model.UserRole;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    public User login(String email, String password, HttpSession session) throws StoreException;

    public String register(UserRole role, User user) throws StoreException;

    public boolean isLoggedIn(UserRole role, HttpSession session);

    public boolean logout(HttpSession session);

    public String getNameByUserID(int userID) throws StoreException;

    public String getRoleByUserID(int userID) throws StoreException;

    public List<User> getAllUsers() throws StoreException;

    public void deleteUserByUserID(int userID) throws StoreException;
}
