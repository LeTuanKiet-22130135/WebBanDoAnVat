package controller;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import newdao.CartDAO;
import newdao.UserDAO;
import newmodel.Cart;
import newmodel.CartItem;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	@Serial
	private static final long serialVersionUID = 1L;

	private final UserDAO userDAO = new UserDAO();
	private final CartDAO cartDAO = new CartDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// Validate input
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			req.setAttribute("error", "Username and password are required.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		// Check if user exists
		if (!userDAO.checkUser(username)) {
			req.setAttribute("error", "Invalid username or password.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		// Check if password is correct
		if (!userDAO.checkPassword(username, password)) {
			req.setAttribute("error", "Invalid username or password.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		// Authentication successful, create session
		HttpSession session = req.getSession();
		session.setAttribute("username", username);

		// Get user ID
		int userId = userDAO.getUserIdByUsername(username);

		// Get user's cart and calculate total items
		Cart cart = cartDAO.getCartByUserId(userId);
		session.setAttribute("cart", cart);
		session.setAttribute("cartSubtotal", cart.getSubtotal());

		// Calculate total items count and store in session
		int totalItems = 0;
		if (cart.getItems() != null) {
			for (CartItem item : cart.getItems()) {
				totalItems += item.getQuantity();
			}
		}
		session.setAttribute("cartItemCount", totalItems);

		// Redirect to index page
		resp.sendRedirect("index");
	}
}
