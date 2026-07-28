import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            out.print("{\"success\": false, \"message\": \"Username and password required\"}");
            return;
        }

        User user = UserDAO.validateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            out.print("{\"success\": true, \"message\": \"Login successful\", \"role\": \"" + user.getRole() + "\", \"fullName\": \"" + user.getFullName() + "\"}");
        } else {
            out.print("{\"success\": false, \"message\": \"Invalid Username or Password\"}");
        }
    }
}
