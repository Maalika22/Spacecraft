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
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
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
            String searchType = request.getParameter("type");
            String searchTerm = request.getParameter("term");

            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                out.println("{\"success\": false, \"message\": \"Search term is required\"}");
                return;
            }

            List<Parameter> results = null;

            switch (searchType) {
                case "pageId":
                    results = parameterDAO.searchByPageId(searchTerm);
                    break;
                case "spacecraft":
                    results = parameterDAO.searchBySpacecraftName(searchTerm);
                    break;
                case "subsystem":
                    results = parameterDAO.searchBySubsystemName(searchTerm);
                    break;
                default:
                    out.println("{\"success\": false, \"message\": \"Invalid search type\"}");
                    return;
            }

            if (results != null) {
                out.println(convertParametersToJson(results));
            } else {
                out.println("{\"success\": false, \"message\": \"No results found\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    private String convertParametersToJson(List<Parameter> parameters) {
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"data\": [");

        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) {
                json.append(",");
            }

            Parameter param = parameters.get(i);
            json.append("{");
            json.append("\"pageId\": \"").append(param.getPageId()).append("\",");
            json.append("\"pageNo\": ").append(param.getPageNo()).append(",");
            json.append("\"spacecraftName\": \"").append(param.getSpacecraftName()).append("\",");
            json.append("\"subsystemName\": \"").append(param.getSubsystemName()).append("\"");

            for (int j = 1; j <= 38; j++) {
                json.append(",\"param").append(j).append("\": \"");
                String paramValue = param.getParameter(j);
                json.append(paramValue != null ? paramValue : "").append("\"");
            }

            json.append("}");
        }

        json.append("]}");
        return json.toString();
    }
}
