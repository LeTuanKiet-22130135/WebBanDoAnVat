package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.*;
import java.io.*;
import java.util.*;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RemoveFromCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");

        String productId = request.getParameter("productId");

        products.removeIf(product -> String.valueOf(product.getId()).equals(productId));

        session.setAttribute("products", products);
        response.sendRedirect("cart.jsp");
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
