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

@WebServlet("/save")
public class SaveParameterServlet extends HttpServlet {
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
            String pageNoStr = request.getParameter("pageNo");
            int pageNo = 1;

            try {
                if (pageNoStr != null && !pageNoStr.trim().isEmpty()) {
                    pageNo = Integer.parseInt(pageNoStr);
                }
            } catch (NumberFormatException e) {
                // Use default page number 1
            }

            if (spacecraftName == null || spacecraftName.trim().isEmpty() ||
                    subsystemName == null || subsystemName.trim().isEmpty()) {
                out.println("{\"success\": false, \"message\": \"Spacecraft name and subsystem name are required\"}");
                return;
            }

            Parameter parameter = new Parameter(pageId, spacecraftName, subsystemName);
            parameter.setPageNo(pageNo);

            // Set all 38 parameters
            for (int i = 1; i <= 38; i++) {
                String paramValue = request.getParameter("param" + i);
                parameter.setParameter(i, paramValue);
            }

            boolean success = parameterDAO.save(parameter);

            if (success) {
                out.println("{\"success\": true, \"message\": \"Parameter saved successfully\", \"pageId\": \""
                        + parameter.getPageId() + "\"}");
            } else {
                out.println("{\"success\": false, \"message\": \"Failed to save parameter\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }
}
