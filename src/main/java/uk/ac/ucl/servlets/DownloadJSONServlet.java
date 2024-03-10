package uk.ac.ucl.servlets;

import uk.ac.ucl.main.DataFrame;
import uk.ac.ucl.main.JSONWriter;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadJSON.html")
public class DownloadJSONServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the data from the model
        Model model = ModelFactory.getModel();
        DataFrame dataFrame = model.getDataFrame(); // Assuming you have a method to get the DataFrame

        // Write DataFrame to JSON file
        String filePath = "data/output.json";
        JSONWriter.writeDataFrameToJSON(dataFrame, filePath);

        // Set response content type
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.json\"");

        // Send the JSON file as response
        try (OutputStream outputStream = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

