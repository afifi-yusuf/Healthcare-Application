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

@WebServlet("/gendergraph.html")
public class GenderGraphServlet extends HttpServlet {
    // Call the addPatient method in your model to add the new patient
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        Model model = ModelFactory.getModel();
        model.createGenderDistributionPieChart("/Users/yusufafifi/Desktop/OOP/OOP_Coursework/src/main/webapp/images/genderDistribution.png");
        request.setAttribute("result", "Gender distribution graph created");

        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/genderGraph.jsp");
        dispatch.forward(request, response);
    }
}
