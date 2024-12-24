package admincontroller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.Product;
import dao.ProductDAO;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the Add Product form
        request.getRequestDispatcher("AddProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Add Product Logic
        Product product = extractProductFromRequest(request);
        productDAO.addProduct(product);
        response.sendRedirect("ProductControl");
    }

    private Product extractProductFromRequest(HttpServletRequest request) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setImageUrl(request.getParameter("imageUrl"));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        return product;
    }
}
