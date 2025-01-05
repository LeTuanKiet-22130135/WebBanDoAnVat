package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

import dao.CartDAO;
import model.Cart;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {   

        String username = request.getUserPrincipal().getName();
        String productIdParam = request.getParameter("productId");

        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);
            int cartId = cartDAO.getCartIdByUsername(username);

            if (cartId == -1) {
                response.sendRedirect("cart");
                return;
            }

            // Remove the item from the cart using CartDAO
            cartDAO.removeItemFromCart(cartId, productId);

            // Update the session cart (if present)
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart != null) {
                    cart.getItems().removeIf(item -> item.getProductId() == productId);
                    session.setAttribute("cart", cart);
                }
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error while removing the item from cart.", e);
        }

        // Redirect back to the cart page
        response.sendRedirect("cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
