package aigoo404.admin;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import newmodel.User;

import java.io.IOException;

@WebFilter({ "/admin/adminDashboard.jsp", "/admin/orderManagement.jsp",
        "/admin/userManagement.jsp", "/admin/productManagement.jsp" })
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;

        if (authUser == null) {
            session = req.getSession(true); // Ensure session exists
            session.setAttribute("error", "Vui lòng đăng nhập trước!");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if (authUser.getStatus() == 2 || authUser.getStatus() == 3) {
            chain.doFilter(request, response);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    public void destroy() {
    }
}
