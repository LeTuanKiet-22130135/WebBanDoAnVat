package controller;
import java.io.IOException;
import java.util.List;

import dao.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ProductDAO dao = new ProductDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = dao.getAllProducts();       
		req.setAttribute("products", products);       
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
