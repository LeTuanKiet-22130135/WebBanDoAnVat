package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

import dao.CartDAO;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getUserPrincipal().getName();
        int productId = Integer.parseInt(request.getParameter("productId"));
        String action = request.getParameter("action");

        try {
            // Fetch or validate the cart ID using CartDAO
            int cartId = cartDAO.getCartIdByUsername(username);
            if (cartId == -1) {
                response.sendRedirect("cart");
                return;
            }

            // Update the cart item quantity based on the action
            if ("increase".equals(action)) {
                cartDAO.updateCartItemQuantity(cartId, productId, 1);
            } else if ("decrease".equals(action)) {
                cartDAO.updateCartItemQuantity(cartId, productId, -1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error updating cart item quantity.");
        }

        // Redirect back to cart page
        response.sendRedirect("cart");
    }
}
