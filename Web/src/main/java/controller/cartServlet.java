package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import db.*;

@WebServlet("/cartServlet")
public class cartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public cartServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product";

		try (
				
				Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()
						) {

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setImageUrl(rs.getString("imageUrl"));
				product.setQuantity(rs.getInt("quantity"));
				
			}
			
			 // Đưa dữ liệu vào session
            HttpSession session = request.getSession();
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
		doGet(request, response);
	}

}
