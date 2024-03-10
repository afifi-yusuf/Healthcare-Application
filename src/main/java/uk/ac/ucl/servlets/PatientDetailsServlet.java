package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/patientDetails")
public class PatientDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch patient details from your data source
        // For example, retrieve patient ID from request parameters
        String patient = request.getParameter("patient");

        // Fetch patient details based on patientId
        Model model = ModelFactory.getModel();
        String patientId = model.getPatientId(patient);
        String searchResult = model.displayPatientDetails(patientId);
        request.setAttribute("result", searchResult);
        // Forward the request to a JSP page to display the patient details
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/patientDetails.jsp");
        dispatch.forward(request, response);
    }
}
