package controller;

import dao.ProfileDAO;
import dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Profile;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfileDAO profileDAO = new ProfileDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get user ID 
        String username = request.getUserPrincipal().getName();
        int userId = userDAO.getUserIdByUsername(username);

        // Fetch profile from the database
        Profile profile = profileDAO.getProfileByUserId(userId);

        // Set profile attribute and forward to JSP
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get user ID from the session
        int userId = userDAO.getUserIdByUsername(request.getUserPrincipal().getName());

        // Create profile object and populate fields
        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setFirstName(request.getParameter("firstName"));
        profile.setLastName(request.getParameter("lastName"));
        profile.setEmail(request.getParameter("email"));
        profile.setMobileNo(request.getParameter("mobileNo"));
        profile.setAddressLine1(request.getParameter("addressLine1"));
        profile.setAddressLine2(request.getParameter("addressLine2"));
        profile.setCountry(request.getParameter("country"));
        profile.setCity(request.getParameter("city"));
        profile.setState(request.getParameter("state"));
        profile.setZipCode(request.getParameter("zipCode"));

        // Update the profile in the database
        profileDAO.updateProfile(profile);

        // Redirect back to profile page
        response.sendRedirect("profile");
    }
}
