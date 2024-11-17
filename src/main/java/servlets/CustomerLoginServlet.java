package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DreamerGp2024.constant.BookStoreConstants;
import com.DreamerGp2024.constant.db.UsersDBConstants;
import com.DreamerGp2024.model.User;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.UserService;
import com.DreamerGp2024.service.impl.UserServiceImpl;

public class CustomerLoginServlet extends HttpServlet {

    UserService authService = new UserServiceImpl();

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
        String email = req.getParameter(UsersDBConstants.COLUMN_EMAIL);
        String password = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);

        User user = authService.login(email, password, req.getSession());

        try {

            if (user != null) {

                RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
                rd.include(req, res);
                pw.println("    <div id=\"topmid\"><h1>Welcome to Online <br>Book Store</h1></div>\r\n"
                        + "    <br>\r\n"
                        + "    <table class=\"tab\">\r\n"
                        + "        <tr>\r\n"
                        + "            <td><p>Welcome "+user.getFirstName()+", Happy Learning !!</p></td>\r\n"
                        + "        </tr>\r\n"
                        + "    </table>");

            } else {

                RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\"><tr><td>Incorrect UserName or PassWord</td></tr></table>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}