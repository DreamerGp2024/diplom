package servlets;

import com.DreamerGp2024.constant.BookStoreConstants;
import com.DreamerGp2024.model.*;
import com.DreamerGp2024.service.BookService;
import com.DreamerGp2024.service.impl.BookServiceImpl;
import com.DreamerGp2024.service.impl.OrderServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessPaymentServlet extends HttpServlet {

    BookService bookService = new BookServiceImpl();

    @SuppressWarnings("unchecked")
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
        if (!StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {
            OrderServiceImpl orderService = new OrderServiceImpl();
            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "cart");
            pw.println("<div id='topmid' style='background-color:grey'>Your Orders</div>");
            pw.println("<div class=\"container\">\r\n"
                    + "        <div class=\"card-columns\">");
            HttpSession session = req.getSession();
            List<Cart> cartItems = null;
            if (session.getAttribute("cartItems") != null) {
                cartItems = (List<Cart>) session.getAttribute("cartItems");
            }
            Random random = new Random();
            int orderID = random.nextInt(Integer.MAX_VALUE);
            double total=0;
            for (Cart cart : cartItems) {
                ArrayList<String> list = new ArrayList<>();
                list.add(cart.getBook().getBarcode());
                ArrayList<Double> list1 = new ArrayList<>();
                list1.add(cart.getBook().getPrice());
                ArrayList<Integer> list2 = new ArrayList<>();
                list2.add(cart.getQuantity());

                for (Double elements : list1)
                    total += elements;

                Order order = new Order( orderID,(Integer) req.getSession().getAttribute("userID"), list, list1,list2, total,0,  OrderStatus.NEW  );
                orderService.addOrder(order);
                Book book = cart.getBook();
                double bPrice = book.getPrice();
                String bCode = book.getBarcode();
                String bName = book.getName();
                String bAuthor = book.getAuthor();
                int availableQty = book.getQuantity();
                int qtToBuy = cart.getQuantity();
                availableQty = availableQty - qtToBuy;
                bookService.updateBookQtyById(bCode, availableQty);
                pw.println(this.addBookToCard(bCode, bName, bAuthor, bPrice, availableQty));
                session.removeAttribute("qty_" + bCode);
            }
            session.removeAttribute("amountToPay");
            session.removeAttribute("cartItems");
            session.removeAttribute("items");
            session.removeAttribute("selectedBookId");
            pw.println("</div>\r\n"
                    + "    </div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addBookToCard(String bCode, String bName, String bAuthor, double bPrice, int bQty) {
        String button = "<a href=\"#\" class=\"btn btn-info\">Order Placed</a>\r\n";

        return "<div class=\"card\" style=\"height: 550px;\" >\r\n"
                + "                <div class=\"row card-body\">\r\n"
                + "                    <a style=\"display: inline-block; width: 65%;\" href=\"/onlinebookstore_war/bookDetailsServlet?id="+bCode+"\">"
                + "                        <img style=\"width: 100%;\" src=\"https://webserver.kmt.support/onlinebookstore/"+bCode+".JPG\" alt=\"Card image cap\">\r\n"
                + "                    </a>"
                + "                    <div class=\"col-sm-6\">\r\n"
                + "                        <h5 class=\"card-title text-success\">" + bName + "</h5>\r\n"
                + "                        <p class=\"card-text\">\r\n"
                + "                        Author: <span class=\"text-primary\" style=\"font-weight:bold;\"> "
                + bAuthor
                + "</span><br>\r\n"
                + "                        </p>\r\n"
                + "                        \r\n"
                + "                    </div>\r\n"
                + "                </div>\r\n"
                + "                <div class=\"row card-body\">\r\n"
                + "                    <div class=\"col-sm-6\">\r\n"
                + "                        <p class=\"card-text\">\r\n"
                + "                        <span style='color:blue;'>Order Id: ORD" + bCode + "TM </span>\r\n"
                + "                        <br><span class=\"text-danger\">Item Yet to be Delivered</span>\r\n"
                + "                        </p>\r\n"
                + "                    </div>\r\n"
                + "                    <div class=\"col-sm-6\">\r\n"
                + "                        <p class=\"card-text\">\r\n"
                + "                        Amout Paid: <span style=\"font-weight:bold; color:green\"> &#8364; " + bPrice
                + " </span>\r\n"
                + "                        </p>\r\n"
                + button
                + "                    </div>\r\n"
                + "                </div>\r\n"
                + "            </div>";
    }
}
