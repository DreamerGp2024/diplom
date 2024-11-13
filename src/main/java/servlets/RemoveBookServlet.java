package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DreamerGp2024.constant.ResponseCode;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.BookService;
import com.DreamerGp2024.service.impl.BookServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

public class RemoveBookServlet extends HttpServlet {

    BookService bookService = new BookServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        if (!StoreUtil.isLoggedIn(UserRole.MANAGER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        try {
            String bookId = req.getParameter("bookId");
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "storebooks");
            String responseCode = bookService.deleteBookById(bookId.trim());
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(responseCode)) {
                pw.println("<table class=\"tab my-5\"><tr><td>Book Removed Successfully</td></tr></table>");
            } else {
                pw.println("<table class=\"tab my-5\"><tr><td>Book Not Available In The Store</td></tr></table>");
            }
            pw.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Remove Books! Try Again</td></tr></table>");
        }
    }
}
