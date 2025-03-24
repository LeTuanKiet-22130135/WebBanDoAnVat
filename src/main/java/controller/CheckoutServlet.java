package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import dao.CartDAO;
import dao.OrderDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	
        // Ensure the user is logged in
        if (request.getUserPrincipal() == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("message", "Your cart is empty.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        
        // Retrieve user ID and cart
        String username = req.getUserPrincipal().getName();
        int userId = userDAO.getUserIdByUsername(username);
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> cartItems = cart.getItems();
        BigDecimal totalAmount = cart.getSubtotal().add((BigDecimal) session.getAttribute("shippingCost"));

        // Record the order and order items in the database
        int orderId = orderDAO.createOrder(userId, totalAmount, cartItems);

        if (orderId > 0) {
            // Clear the cart
            cartDAO.clearCart(cart.getId());

            // Show success popup and redirect to index page
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<script type='text/javascript'>");
            resp.getWriter().println("alert('Purchase successful!');");
            resp.getWriter().println("window.location.href = 'index';");
            resp.getWriter().println("</script>");
            resp.getWriter().println("</body></html>");
        } else {
            req.setAttribute("errorMessage", "Failed to process your order. Please try again.");
            req.getRequestDispatcher("checkout.jsp").forward(req, resp);
        }
    }
}
