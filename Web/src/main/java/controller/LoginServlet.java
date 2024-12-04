package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    Connection connection = DBConnection.getConnection();
	    try {
	        // Validate user credentials
	        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
	        PreparedStatement stmt = connection.prepareStatement(sql);
	        stmt.setString(1, username);
	        stmt.setString(2, password);

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            // Successful login: Set user session
	            HttpSession session = req.getSession();
	            session.setAttribute("user", rs.getString("username"));

	            // Redirect to the originally requested page, or default to index
	            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
	            if (redirectUrl != null) {
	                session.removeAttribute("redirectAfterLogin");
	                resp.sendRedirect(redirectUrl);
	            } else {
	                resp.sendRedirect("index"); // Redirect to the main page
	            }
	        } else {
	            // Failed login: Show error message
	            req.setAttribute("errorMessage", "Invalid username or password.");
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
	    }
	}
}
