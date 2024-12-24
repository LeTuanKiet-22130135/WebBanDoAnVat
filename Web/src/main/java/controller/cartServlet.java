package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import dao.*;

@WebServlet("/cart")
public class cartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection conn = DBConnection.getConnection();
	private final UserDAO userDAO = new UserDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Ensure the user is authenticated
		HttpSession session = request.getSession(false);
		if (request.getUserPrincipal().getName() == null) { // Check using container-managed authentication
			response.sendRedirect("login"); // Fallback, though this should not be needed
			return;
		}

		// Fetch the user ID using a session attribute or user principal
		String username = request.getUserPrincipal().getName(); // Get the logged-in username
		int userId = userDAO.getUserIdByUsername(username); // Fetch user ID from the database based on username

		if (userId == -1) {
			throw new ServletException("User ID not found for username: " + username);
		}

		// Initialize cart and fetch its ID
		Cart cart = new Cart();
		cart.setUserId(userId);

		try {
			// Retrieve cart ID for the user
			String cartQuery = "SELECT id FROM Cart WHERE user_id = ?";
			try (PreparedStatement cartStmt = conn.prepareStatement(cartQuery)) {
				cartStmt.setInt(1, userId);
				try (ResultSet cartRs = cartStmt.executeQuery()) {
					if (cartRs.next()) {
						cart.setId(cartRs.getInt("id")); // Set cart ID
					} else {
						// If no cart exists, create one
						String createCartQuery = "INSERT INTO Cart (user_id, created_at) VALUES (?, NOW())";
						try (PreparedStatement createCartStmt = conn.prepareStatement(createCartQuery, Statement.RETURN_GENERATED_KEYS)) {
							createCartStmt.setInt(1, userId);
							createCartStmt.executeUpdate();
							try (ResultSet generatedKeys = createCartStmt.getGeneratedKeys()) {
								if (generatedKeys.next()) {
									cart.setId(generatedKeys.getInt(1)); // Set newly created cart ID
								}
							}
						}
					}
				}
			}

			// Fetch cart items
			String query = """
					SELECT ci.id AS cart_item_id, 
					       p.id AS product_id, 
					       p.name, 
					       p.image AS product_image, 
					       p.price AS product_price, 
					       ci.quantity 
					FROM CartItem ci
					INNER JOIN Product p ON ci.product_id = p.id
					INNER JOIN Cart c ON ci.cart_id = c.id
					WHERE c.user_id = ?;
					""";

			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setInt(1, cart.getId());
				try (ResultSet rs = ps.executeQuery()) {
					List<CartItem> items = new ArrayList<>();
					BigDecimal subtotal = BigDecimal.ZERO;

					// Populate cart items and calculate subtotal
					while (rs.next()) {
						CartItem item = new CartItem();
						item.setId(rs.getInt("cart_item_id"));
						item.setCartId(cart.getId());
						item.setProductId(rs.getInt("product_id"));
						item.setProductName(rs.getString("name"));
						item.setProductImageUrl(rs.getString("product_image"));
						item.setProductPrice(rs.getBigDecimal("product_price"));
						item.setQuantity(rs.getInt("quantity"));

						items.add(item);
						subtotal = subtotal.add(item.getTotalPrice());
					}

					cart.setItems(items);
					request.setAttribute("cartSubtotal", subtotal);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set cart and shipping cost as request attributes
		BigDecimal shippingCost = new BigDecimal("5.00"); // Example shipping cost
		session.setAttribute("cart", cart);
		session.setAttribute("shippingCost", shippingCost);

		// Forward to cart.jsp
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

	    // Ensure the user is authenticated
	    if (request.getUserPrincipal() == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    // Fetch the user ID based on the authenticated principal
	    String username = request.getUserPrincipal().getName();
	    int userId = userDAO.getUserIdByUsername(username);

	    if (userId == -1) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    // Process the add-to-cart action
	    String action = request.getParameter("action");
	    int productId = Integer.parseInt(request.getParameter("productId"));

	    if ("add".equals(action)) {
	        try {
	            // Retrieve or create the cart
	            String cartQuery = "SELECT id FROM Cart WHERE user_id = ?";
	            int cartId = 0;

	            try (PreparedStatement cartStmt = conn.prepareStatement(cartQuery)) {
	                cartStmt.setInt(1, userId);
	                try (ResultSet cartRs = cartStmt.executeQuery()) {
	                    if (cartRs.next()) {
	                        cartId = cartRs.getInt("id");
	                    } else {
	                        String createCartQuery = "INSERT INTO Cart (user_id, created_at) VALUES (?, NOW())";
	                        try (PreparedStatement createCartStmt = conn.prepareStatement(createCartQuery, Statement.RETURN_GENERATED_KEYS)) {
	                            createCartStmt.setInt(1, userId);
	                            createCartStmt.executeUpdate();
	                            try (ResultSet generatedKeys = createCartStmt.getGeneratedKeys()) {
	                                if (generatedKeys.next()) {
	                                    cartId = generatedKeys.getInt(1);
	                                }
	                            }
	                        }
	                    }
	                }
	            }

	            // Add or update the product in the cart
	            String cartItemQuery = "SELECT id, quantity FROM CartItem WHERE cart_id = ? AND product_id = ?";
	            try (PreparedStatement cartItemStmt = conn.prepareStatement(cartItemQuery)) {
	                cartItemStmt.setInt(1, cartId);
	                cartItemStmt.setInt(2, productId);
	                try (ResultSet cartItemRs = cartItemStmt.executeQuery()) {
	                    if (cartItemRs.next()) {
	                        int existingQuantity = cartItemRs.getInt("quantity");
	                        String updateQuery = "UPDATE CartItem SET quantity = ? WHERE id = ?";
	                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
	                            updateStmt.setInt(1, existingQuantity + 1);
	                            updateStmt.setInt(2, cartItemRs.getInt("id"));
	                            updateStmt.executeUpdate();
	                        }
	                    } else {
	                        String insertQuery = "INSERT INTO CartItem (cart_id, product_id, quantity) VALUES (?, ?, ?)";
	                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	                            insertStmt.setInt(1, cartId);
	                            insertStmt.setInt(2, productId);
	                            insertStmt.setInt(3, 1);
	                            insertStmt.executeUpdate();
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Redirect to cart page
	    response.sendRedirect("cart");
	}

}
