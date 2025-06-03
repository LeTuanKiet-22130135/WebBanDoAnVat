package controller;

import util.VnPayUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "VnPayReturnServlet", urlPatterns = {"/vnpay_return"})
public class VnPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> vnp_Params = new HashMap<>();

        // Get all parameters from the request
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            if (paramValue != null && !paramValue.isEmpty()) {
                vnp_Params.put(paramName, paramValue);
            }
        }

        // Remove vnp_SecureHash from the map to verify the checksum
        if (vnp_Params.containsKey("vnp_SecureHash")) {
            String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
            vnp_Params.remove("vnp_SecureHash");

            // Remove vnp_SecureHashType if present
            if (vnp_Params.containsKey("vnp_SecureHashType")) {
                vnp_Params.remove("vnp_SecureHashType");
            }

            // Verify the checksum
            String signValue = VnPayUtil.hashAllFields(vnp_Params);
            if (signValue.equals(vnp_SecureHash)) {
                // Valid signature
                String vnp_ResponseCode = vnp_Params.get("vnp_ResponseCode");
                if ("00".equals(vnp_ResponseCode)) {
                    // Payment successful
                    String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
                    String vnp_Amount = vnp_Params.get("vnp_Amount");
                    String vnp_OrderInfo = vnp_Params.get("vnp_OrderInfo");
                    String vnp_BankCode = vnp_Params.get("vnp_BankCode");
                    String vnp_PayDate = vnp_Params.get("vnp_PayDate");

                    // Get session and required DAOs
                    HttpSession session = request.getSession();
                    newdao.OrderDAO orderDAO = new newdao.OrderDAO();
                    newdao.CartDAO cartDAO = new newdao.CartDAO();

                    // Get pending order information from session
                    Integer pendingUserId = (Integer) session.getAttribute("pendingUserId");
                    @SuppressWarnings("unchecked")
                    List<newmodel.CartItem> pendingCartItems = (List<newmodel.CartItem>) session.getAttribute("pendingCartItems");
                    BigDecimal pendingTotalAmount = (BigDecimal) session.getAttribute("pendingTotalAmount");
                    newmodel.Cart cart = (newmodel.Cart) session.getAttribute("cart");

                    if (pendingUserId != null && pendingCartItems != null && pendingTotalAmount != null) {
                        // Create the order now that payment is successful
                        int orderId = orderDAO.createOrder(pendingUserId.intValue(), pendingTotalAmount, pendingCartItems);

                        if (orderId > 0) {
                            // Add shipping information with payment status = 1 (paid)
                            int shippingId = orderDAO.addShipping(orderId, 0, 1); // 0 = placed, 1 = paid

                            // Clear cart
                            if (cart != null) {
                                cartDAO.clearCart(cart.getId());
                            }

                            // Clear pending order information from session
                            session.removeAttribute("pendingUserId");
                            session.removeAttribute("pendingCartItems");
                            session.removeAttribute("pendingTotalAmount");
                            session.removeAttribute("tempOrderRef");

                            // Store payment information in session
                            session.setAttribute("vnp_TxnRef", vnp_TxnRef);
                            session.setAttribute("vnp_Amount", vnp_Amount);
                            session.setAttribute("vnp_OrderInfo", vnp_OrderInfo);
                            session.setAttribute("vnp_BankCode", vnp_BankCode);
                            session.setAttribute("vnp_PayDate", vnp_PayDate);
                            session.setAttribute("paymentStatus", "success");

                            // Redirect to success page
                            response.sendRedirect(request.getContextPath() + "/payment-success.jsp");
                        } else {
                            // Failed to create order
                            session.setAttribute("paymentStatus", "failed");
                            session.setAttribute("errorMessage", "Payment was successful but order creation failed");

                            // Redirect to failure page
                            response.sendRedirect(request.getContextPath() + "/payment-failed.jsp");
                        }
                    } else {
                        // Missing order information
                        session.setAttribute("paymentStatus", "failed");
                        session.setAttribute("errorMessage", "Payment was successful but order information was missing");

                        // Redirect to failure page
                        response.sendRedirect(request.getContextPath() + "/payment-failed.jsp");
                    }
                } else {
                    // Payment failed
                    HttpSession session = request.getSession();
                    session.setAttribute("paymentStatus", "failed");
                    session.setAttribute("vnp_ResponseCode", vnp_ResponseCode);

                    // Redirect to failure page
                    response.sendRedirect(request.getContextPath() + "/payment-failed.jsp");
                }
            } else {
                // Invalid signature
                HttpSession session = request.getSession();
                session.setAttribute("paymentStatus", "invalid");

                // Redirect to failure page
                response.sendRedirect(request.getContextPath() + "/payment-failed.jsp");
            }
        } else {
            // No secure hash found
            HttpSession session = request.getSession();
            session.setAttribute("paymentStatus", "invalid");

            // Redirect to failure page
            response.sendRedirect(request.getContextPath() + "/payment-failed.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
