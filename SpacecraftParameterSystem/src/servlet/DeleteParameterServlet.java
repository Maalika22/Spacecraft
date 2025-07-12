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

@WebServlet("/delete")
public class DeleteParameterServlet extends HttpServlet {
    private ParameterDAO parameterDAO;

    @Override
    public void init() throws ServletException {
        parameterDAO = new ParameterDAO();
        DatabaseUtil.initializeDatabase();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String pageId = request.getParameter("pageId");
            String spacecraftName = request.getParameter("spacecraftName");
            String subsystemName = request.getParameter("subsystemName");

            if (pageId == null || pageId.trim().isEmpty() ||
                    spacecraftName == null || spacecraftName.trim().isEmpty() ||
                    subsystemName == null || subsystemName.trim().isEmpty()) {
                out.println(
                        "{\"success\": false, \"message\": \"Page ID, spacecraft name, and subsystem name are required\"}");
                return;
            }

            boolean success = parameterDAO.delete(pageId, spacecraftName, subsystemName);

            if (success) {
                out.println("{\"success\": true, \"message\": \"Parameter deleted successfully\"}");
            } else {
                out.println("{\"success\": false, \"message\": \"Failed to delete parameter or parameter not found\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }
}
