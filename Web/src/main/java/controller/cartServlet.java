package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import db.*;

@WebServlet("/cart")
public class cartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product";
		Connection conn = DBConnection.getConnection();
		
		HttpSession session = request.getSession(false);
		
		// Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            // Save the current URL for redirection after login
            String redirectUrl = request.getRequestURL() + "?" + request.getQueryString();
            session = request.getSession(true);
            session.setAttribute("redirectAfterLogin", redirectUrl);

            // Redirect to login page
            response.sendRedirect("login.jsp");
            return;
        }
		
		try (
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
		) {

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setImageUrl(rs.getString("image"));
				product.setQuantity(rs.getInt("cid"));
				products.add(product);
			}
			
			System.out.println(products.size());

			// Đưa dữ liệu vào session
			session.setAttribute("products", products);

			// Chuyển đến trang cart.jsp
			request.getRequestDispatcher("cart.jsp").forward(request, response);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
