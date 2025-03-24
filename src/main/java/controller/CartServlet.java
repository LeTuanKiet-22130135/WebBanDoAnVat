package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import dao.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (request.getUserPrincipal() == null) { 
            response.sendRedirect("login");
            return;
        }

        String username = request.getUserPrincipal().getName();
        int userId = userDAO.getUserIdByUsername(username);
        if (userId == -1) {
            throw new ServletException("User ID not found for username: " + username);
        }

        try {
            int cartId = cartDAO.getOrCreateCartId(userId);
            List<CartItem> items = cartDAO.getCartItems(cartId);

            BigDecimal subtotal = items.stream()
                                       .map(CartItem::getTotalPrice)
                                       .reduce(BigDecimal.ZERO, BigDecimal::add);

            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setId(cartId);
            cart.setItems(items);

            session.setAttribute("cart", cart);
            session.setAttribute("cartSubtotal", subtotal);
            session.setAttribute("shippingCost", new BigDecimal("5.00"));
            request.getRequestDispatcher("cart.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error while retrieving cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getUserPrincipal() == null) {
        	System.out.println(request.getUserPrincipal());
            response.sendRedirect("login");
            return;
        }

        String username = request.getUserPrincipal().getName();
        int userId = userDAO.getUserIdByUsername(username);
        if (userId == -1) {
        	System.out.println(userId);
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("productId"));

        if ("add".equals(action)) {
            try {
                int cartId = cartDAO.getOrCreateCartId(userId);
                cartDAO.addOrUpdateCartItem(cartId, productId);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Database error while adding product to cart");
            }
        }

        response.sendRedirect("cart");
    }
}
