package servlets;

import com.DreamerGp2024.service.BookService;
import com.DreamerGp2024.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookDetailsServlet extends HttpServlet {
    BookService bookService = new BookServiceImpl();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String barcode = request.getParameter("id");
        request.setAttribute("book", bookService.getBookById(barcode));
        request.getRequestDispatcher("/BookInfo.jsp").forward(request, response);
    }
}
