package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;
import java.io.*;
import java.util.*;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateCartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");

		String productId = request.getParameter("productId");
		String action = request.getParameter("action");
		
		for (Product product : products) {
            if (String.valueOf(product.getId()).equals(productId)) {
                if ("increase".equals(action)) {
                    product.setQuantity(product.getQuantity() + 1);
                } else if ("decrease".equals(action) && product.getQuantity() > 1) {
                    product.setQuantity(product.getQuantity() - 1);
                }
                break;
            }
        }

        session.setAttribute("products", products);
        response.sendRedirect("cart.jsp");
    }
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
