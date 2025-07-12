package servlet;

import dao.ParameterDAO;
import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/autocomplete")
public class AutocompleteServlet extends HttpServlet {
    private ParameterDAO parameterDAO;

    @Override
    public void init() throws ServletException {
        parameterDAO = new ParameterDAO();
        DatabaseUtil.initializeDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String type = request.getParameter("type");
            System.out.println("AutocompleteServlet called with type: " + type); // Debug log

            if ("spacecraft".equals(type)) {
                List<String> spacecraftNames = parameterDAO.getDistinctSpacecraftNames();
                System.out.println("Found " + spacecraftNames.size() + " spacecraft names"); // Debug log
                out.println(convertListToJson(spacecraftNames));
            } else if ("subsystem".equals(type)) {
                List<String> subsystemNames = parameterDAO.getDistinctSubsystemNames();
                System.out.println("Found " + subsystemNames.size() + " subsystem names"); // Debug log
                out.println(convertListToJson(subsystemNames));
            } else {
                out.println("{\"success\": false, \"message\": \"Invalid type parameter\"}");
            }

        } catch (Exception e) {
            System.err.println("Error in AutocompleteServlet: " + e.getMessage()); // Debug log
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    private String convertListToJson(List<String> items) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append("\"").append(items.get(i)).append("\"");
        }

        json.append("]");
        return json.toString();
    }
}
