package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Connection conn = DBConnection.getConnection();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
			
		// Check if the user has a valid cart in session
		if (session == null || session.getAttribute("cart") == null) {
			response.sendRedirect("cart.jsp");
			return;
		}

		// Retrieve the cart and productId from the request
		Cart cart = (Cart) session.getAttribute("cart");
		String productIdParam = request.getParameter("productId");
		
		if (productIdParam == null || productIdParam.isEmpty()) {
			response.sendRedirect("cart.jsp");
			return;
		}

		try {	
			int productId = Integer.parseInt(productIdParam);

			// SQL query to remove the cart item from the database
			String deleteQuery = "DELETE FROM cartitem WHERE cart_id = ? AND product_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, cart.getId());
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();

			// Update the cart object in session by removing the item
			List<CartItem> items = cart.getItems();
			items.removeIf(item -> item.getProductId() == productId);
			cart.setItems(items);
			session.setAttribute("cart", cart);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			throw new ServletException("Error while removing the item from cart.", e);
		}

		// Redirect back to the cart page
		response.sendRedirect("cart");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
