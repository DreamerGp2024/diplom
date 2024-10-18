package com.DreamerGp2024.service;

import javax.servlet.http.HttpSession;

import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.model.User;
import com.DreamerGp2024.model.UserRole;

public interface UserService {

    public User login(UserRole role, String email, String password, HttpSession session) throws StoreException;

    public String register(UserRole role, User user) throws StoreException;

    public boolean isLoggedIn(UserRole role, HttpSession session);

    public boolean logout(HttpSession session);

}
