package controller;

import java.io.IOException;
import java.util.List;

import dao.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet("/orderhistory")
public class OrderHistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getUserPrincipal() == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getUserPrincipal().getName();
        int userId = orderDAO.getUserIdByUsername(username);

        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        request.setAttribute("orders", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("orderhistory.jsp");
        dispatcher.forward(request, response);
    }
}
