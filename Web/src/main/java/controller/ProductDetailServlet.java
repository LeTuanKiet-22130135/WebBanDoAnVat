package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/detail")
public class ProductDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lay product id
		String productId = req.getParameter("id");
		if (productId == null || productId.isEmpty()) {
			resp.sendRedirect("index.jsp");
			return;
		}

		Connection connection = DBConnection.getConnection();
		Product product = null;

		try {
			// Fetch product details
			String productQuery = "SELECT * FROM product WHERE id = ?";
			PreparedStatement productStmt = connection.prepareStatement(productQuery);
			productStmt.setInt(1, Integer.parseInt(productId)); // Set the product ID
			ResultSet rs = productStmt.executeQuery();
			if (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setImageUrl(rs.getString("image"));
				product.setQuantity(rs.getInt("quantity"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("product", product);
		req.getRequestDispatcher("detail.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
}
