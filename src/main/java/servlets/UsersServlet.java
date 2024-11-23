package servlets;

import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.UserService;
import com.DreamerGp2024.service.impl.UserServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!StoreUtil.isLoggedIn(UserRole.ADMIN, request.getSession())) {
            PrintWriter pw = response.getWriter();
            RequestDispatcher rd = request.getRequestDispatcher("SellerLogin.html");
            rd.include(request, response);
            pw.println("<table class=\"tab\"><tr><td>Please Login As Admin To Continue!!</td></tr></table>");
            return;
        }
        request.setAttribute("users", userService.getAllUsers());
        System.out.println(userService.getAllUsers().get(0));
        request.getRequestDispatcher("/UsersList.jsp").forward(request, response);
    }


}