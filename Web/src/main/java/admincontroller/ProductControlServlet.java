package admincontroller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import dao.ProductDAO;

import java.io.IOException;

@WebServlet("/admin/ProductControl")
public class ProductControlServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            deleteProduct(request, response);
        } else {
            displayProductList(request, response);
        }
    }

    private void displayProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load all products from the database
        request.setAttribute("products", productDAO.getAllProducts());
        // Forward to the product control JSP page
        request.getRequestDispatcher("ProductControl.jsp").forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid product ID.");
        }
        // Reload the product list after deletion
        response.sendRedirect("ProductControl");
    }
}
