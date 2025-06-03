package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import newdao.CartDAO;
import newdao.OrderDAO;
import newdao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import newmodel.Cart;
import newmodel.CartItem;

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
        if (session.getAttribute("username") == null) {
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
        String username = (String) session.getAttribute("username");
        int userId = orderDAO.getUserIdByUsername(username);
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> cartItems = cart.getItems();
        BigDecimal totalAmount = cart.getSubtotal().add((BigDecimal) session.getAttribute("shippingCost"));

        // Get the selected payment method
        String paymentMethod = req.getParameter("payment");

        if ("vnpay".equals(paymentMethod)) {
            // For VnPay payments, store order information in session for later use
            // after payment is successful
            session.setAttribute("pendingUserId", userId);
            session.setAttribute("pendingCartItems", cartItems);
            session.setAttribute("pendingTotalAmount", totalAmount);

            // Generate a temporary reference for the transaction
            String tempOrderRef = "TEMP_" + System.currentTimeMillis();
            session.setAttribute("tempOrderRef", tempOrderRef);

            // Redirect to VnPay payment servlet
            resp.sendRedirect(req.getContextPath() + "/vnpay-payment?orderRef=" + tempOrderRef + "&amount=" + totalAmount.multiply(new BigDecimal(100)).intValue());
        } else {
            // For direct check payment, create the order immediately
            int orderId = orderDAO.createOrder(userId, totalAmount, cartItems);

            if (orderId > 0) {
                // Add shipping information
                int shippingId = orderDAO.addShipping(orderId, 0, 1); // 0 = placed, 1 = paid (for direct check)

                // Clear cart
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
}
