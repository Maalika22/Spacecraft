package servlet;

import dao.ParameterDAO;
import model.Parameter;
import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/load")
public class LoadParameterServlet extends HttpServlet {
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

            Parameter parameter = parameterDAO.findByKeys(pageId, spacecraftName, subsystemName);

            if (parameter != null) {
                StringBuilder json = new StringBuilder();
                json.append("{\"success\": true, \"data\": {");
                json.append("\"pageId\": \"").append(parameter.getPageId()).append("\",");
                json.append("\"pageNo\": ").append(parameter.getPageNo()).append(",");
                json.append("\"spacecraftName\": \"").append(parameter.getSpacecraftName()).append("\",");
                json.append("\"subsystemName\": \"").append(parameter.getSubsystemName()).append("\"");

                for (int i = 1; i <= 38; i++) {
                    json.append(",\"param").append(i).append("\": \"");
                    String paramValue = parameter.getParameter(i);
                    json.append(paramValue != null ? paramValue : "").append("\"");
                }

                json.append("}}");
                out.println(json.toString());
            } else {
                out.println("{\"success\": false, \"message\": \"Parameter not found\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }
}
