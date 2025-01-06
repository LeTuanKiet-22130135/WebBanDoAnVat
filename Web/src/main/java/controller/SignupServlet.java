package controller;

import util.CodeGenerator;
import util.EmailUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("signup.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // Generate verification code
        String verificationCode = CodeGenerator.generateVerificationCode();

        // Store user details and code in session (for verification step)
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("email", email);
        session.setAttribute("verificationCode", verificationCode);

        // Send verification email
        EmailUtil.sendVerificationEmail(email, verificationCode);

        // Redirect to verify.jsp
        response.sendRedirect("verify");
    }
}
