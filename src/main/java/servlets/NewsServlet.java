package servlets;

import com.DreamerGp2024.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewsServlet extends HttpServlet {
    PostServiceImpl service = new PostServiceImpl();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("posts", service.getAllPosts());
        request.getRequestDispatcher("/News.jsp").forward(request, response);
    }
}
