package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import util.DatabaseUtil;
import dao.ParameterDAO;
import java.util.List;

@WebServlet("/test-db")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Database Test</title></head><body>");
        out.println("<h1>Database Connection Test</h1>");

        try {
            // Test database connection
            try (Connection conn = DatabaseUtil.getConnection()) {
                out.println("<p style='color: green;'>✓ Database connection successful</p>");
                out.println("<p>Database file: " + System.getProperty("user.dir") + "/spacecraft_parameters.db</p>");
            }

            // Test database initialization
            DatabaseUtil.initializeDatabase();
            out.println("<p style='color: green;'>✓ Database initialization successful</p>");

            // Test DAO operations
            ParameterDAO dao = new ParameterDAO();

            // Test spacecraft names
            List<String> spacecraftNames = dao.getDistinctSpacecraftNames();
            out.println("<h3>Spacecraft Names (" + spacecraftNames.size() + " total):</h3>");
            out.println("<ul>");
            for (String name : spacecraftNames) {
                out.println("<li>" + name + "</li>");
            }
            out.println("</ul>");

            // Test subsystem names
            List<String> subsystemNames = dao.getDistinctSubsystemNames();
            out.println("<h3>Subsystem Names (" + subsystemNames.size() + " total):</h3>");
            out.println("<ul>");
            for (String name : subsystemNames) {
                out.println("<li>" + name + "</li>");
            }
            out.println("</ul>");

            out.println("<p style='color: green;'>✓ All database operations successful!</p>");

        } catch (Exception e) {
            out.println("<p style='color: red;'>✗ Database error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("<hr>");
        out.println("<p><a href='index.jsp'>Back to Main Application</a></p>");
        out.println("</body></html>");
    }
}
