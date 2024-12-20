package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*") // Apply the filter to all URLs
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        
        // Skip static resources or non-protected pages
        if (requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is logged in
        if (session != null && session.getAttribute("user") != null) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

            // If the user is an admin and not already on the admin page, redirect them
            if (Boolean.TRUE.equals(isAdmin)) {
                if (!requestURI.endsWith("/admin/AdminIndex.jsp") && !requestURI.contains("/admin/")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/AdminIndex.jsp");
                    return;
                }
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic, if needed
    }
}
