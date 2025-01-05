package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;
import java.util.List;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryParam = req.getParameter("query");            // Retrieve search term
        String[] priceRanges = req.getParameterValues("priceRange"); // Retrieve selected price ranges

        // Delegate product fetching to ProductDAO
        List<Product> products = productDAO.getProductsByCriteria(queryParam, priceRanges);

        // Check if the request is an AJAX request
        String ajax = req.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajax)) {
            req.setAttribute("products", products);
            req.getRequestDispatcher("WEB-INF/fragment/product-list.jsp").forward(req, resp);
        } else {
            req.setAttribute("products", products);
            req.getRequestDispatcher("shop.jsp").forward(req, resp);
        }
    }
}
