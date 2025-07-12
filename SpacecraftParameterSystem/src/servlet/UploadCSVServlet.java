package servlet;

import dao.ParameterDAO;
import model.Parameter;
import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/upload")
@MultipartConfig
public class UploadCSVServlet extends HttpServlet {
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
            Part filePart = request.getPart("csvFile");

            if (filePart == null) {
                out.println("{\"success\": false, \"message\": \"No file uploaded\"}");
                return;
            }

            String fileName = filePart.getSubmittedFileName();
            if (fileName == null || !fileName.toLowerCase().endsWith(".csv")) {
                out.println("{\"success\": false, \"message\": \"Please upload a CSV file\"}");
                return;
            }

            InputStream fileContent = filePart.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));

            String line;
            boolean isFirstLine = true;
            int successCount = 0;
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }

                String[] values = line.split(",");

                if (values.length < 41) { // pageId + spacecraftName + subsystemName + 38 params
                    errorCount++;
                    continue;
                }

                try {
                    String pageId = values[0].trim();
                    String spacecraftName = values[1].trim();
                    String subsystemName = values[2].trim();

                    Parameter parameter = new Parameter(pageId, spacecraftName, subsystemName);

                    for (int i = 1; i <= 38; i++) {
                        if (i + 2 < values.length) {
                            parameter.setParameter(i, values[i + 2].trim());
                        }
                    }

                    if (parameterDAO.save(parameter)) {
                        successCount++;
                    } else {
                        errorCount++;
                    }

                } catch (Exception e) {
                    errorCount++;
                    e.printStackTrace();
                }
            }

            reader.close();

            out.println("{\"success\": true, \"message\": \"Upload completed. " +
                    "Successfully processed: " + successCount + " records, " +
                    "Errors: " + errorCount + " records\"}");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }
}
