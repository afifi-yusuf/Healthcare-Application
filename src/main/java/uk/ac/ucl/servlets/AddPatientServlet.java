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
import java.util.ArrayList;
import java.util.List;

// The servlet invoked to perform a search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/addPatient.html")
public class AddPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        // Retrieve patient details from the form
        String id = request.getParameter("id");
        String firstName = request.getParameter("first");
        String lastName = request.getParameter("last");
        String dob = request.getParameter("dob");
        String dod = request.getParameter("dod");
        String ssn = request.getParameter("ssn");
        String drivers = request.getParameter("drivers");
        String passport = request.getParameter("passport");
        String prefix = request.getParameter("prefix");
        String suffix = request.getParameter("suffix");
        String maiden = request.getParameter("maiden");
        String marital = request.getParameter("marital");
        String race = request.getParameter("race");
        String ethnicity = request.getParameter("ethnicity");
        String birthplace = request.getParameter("birthplace");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        // Call the addPatient method in your model to add the new patient
        Model model = ModelFactory.getModel();
        model.addPatient(id, firstName, lastName, dob, dod, ssn, drivers, passport, prefix, suffix, maiden, marital, race, ethnicity, birthplace, address, city, state, zip);
        request.setAttribute("result", "Successfully Added Patient");
        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/addPatient.jsp");
        dispatch.forward(request, response);
    }

}