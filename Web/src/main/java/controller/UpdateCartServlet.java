package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DBConnection;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn = DBConnection.getConnection();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Redirect to login if not logged in
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int productId = Integer.parseInt(request.getParameter("productId"));
        String action = request.getParameter("action");

        try {
            // Fetch cart ID for the user
            String cartQuery = "SELECT id FROM Cart WHERE user_id = ?";
            int cartId = -1;
            try (PreparedStatement cartStmt = conn.prepareStatement(cartQuery)) {
                cartStmt.setInt(1, userId);
                var rs = cartStmt.executeQuery();
                if (rs.next()) {
                    cartId = rs.getInt("id");
                } else {
                    response.sendRedirect("cart");
                    return;
                }
            }

            // Update the quantity in the database
            String updateQuery = null;
            if ("increase".equals(action)) {
                updateQuery = "UPDATE CartItem SET quantity = quantity + 1 WHERE cart_id = ? AND product_id = ?";
            } else if ("decrease".equals(action)) {
                updateQuery = "UPDATE CartItem SET quantity = quantity - 1 WHERE cart_id = ? AND product_id = ? AND quantity > 1";
            }

            if (updateQuery != null) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, cartId);
                    updateStmt.setInt(2, productId);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error updating cart item quantity.");
        }

        // Redirect back to cart page
        response.sendRedirect("cart");
    }
}
