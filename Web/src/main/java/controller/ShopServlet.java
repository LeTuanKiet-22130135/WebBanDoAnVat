package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = new ArrayList<>();
		String queryParam = req.getParameter("query"); // Retrieve the search term
		String[] priceRanges = req.getParameterValues("priceRange"); // Retrieve selected price ranges
		Connection connection = DBConnection.getConnection();

		try {
			// Base query
			StringBuilder productQuery = new StringBuilder("SELECT * FROM product");
			List<String> conditions = new ArrayList<>();
			List<Object> parameters = new ArrayList<>();
			
			// Add condition for search term if provided
			if (queryParam != null && !queryParam.trim().isEmpty()) {
				conditions.add("LOWER(name) LIKE ?");
				parameters.add("%" + queryParam.toLowerCase() + "%");
			}

			// Add conditions for selected price ranges if applicable
			if (priceRanges != null && !Arrays.asList(priceRanges).contains("all")) {
				List<String> priceConditions = new ArrayList<>();
				for (String range : priceRanges) {
					switch (range) {
					case "0-10000":
						priceConditions.add("price BETWEEN 0 AND 10000");
						break;
					case "10001-20000":
						priceConditions.add("price BETWEEN 10001 AND 20000");
						break;
					case "20001-30000":
						priceConditions.add("price BETWEEN 20001 AND 30000");
						break;
					case "30001-40000":
						priceConditions.add("price BETWEEN 30001 AND 40000");
						break;
					case "40001-50000":
						priceConditions.add("price BETWEEN 40001 AND 50000");
						break;
					}
				}
				if (!priceConditions.isEmpty()) {
					conditions.add("(" + String.join(" OR ", priceConditions) + ")");
				}
			}

			// Combine conditions if any exist
			if (!conditions.isEmpty()) {
				productQuery.append(" WHERE ").append(String.join(" AND ", conditions));
			}

			PreparedStatement stmt = connection.prepareStatement(productQuery.toString());

			// Set query parameters and log them for debugging
			for (int i = 0; i < parameters.size(); i++) {
				stmt.setObject(i + 1, parameters.get(i));
				System.out.println("Parameter " + (i + 1) + ": " + parameters.get(i));
			}

			// Execute query and build product list
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setImageUrl(rs.getString("image"));
				products.add(product);
			}
		} catch (SQLException e) {
			// Print stack trace and SQL error
			e.printStackTrace();
		}

		// Check if the request is an AJAX request
		String ajax = req.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(ajax)) {
			// Set products as a request attribute and forward to product-list.jsp for AJAX responses
			req.setAttribute("products", products);
			req.getRequestDispatcher("product-list.jsp").forward(req, resp);
		} else {
			// Non-AJAX request: Forward to the main shop.jsp
			req.setAttribute("products", products);
			req.getRequestDispatcher("shop.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
