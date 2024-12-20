package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnection;
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
		HttpSession session = req.getSession(false);
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		req.login(username, password);

		// Set user details in session for additional app logic
		Connection connection = DBConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement("SELECT id, isAdmin FROM user WHERE username = ?")) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				session.setAttribute("userId", rs.getInt("id"));
				session.setAttribute("isAdmin", rs.getBoolean("isAdmin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
