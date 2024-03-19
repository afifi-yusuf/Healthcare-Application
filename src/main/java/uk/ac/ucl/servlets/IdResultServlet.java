package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//Servlet for searching for patient by ID
@WebServlet("/runidsearch.html")
public class IdResultServlet extends BaseSearchServlet {
    @Override
    protected String getSearchResult(HttpServletRequest request) throws IOException {
        // Use the model to do the search and return the search result
        return ModelFactory.getModel().displayPatientDetails(request.getParameter("searchstring"));
    }

    @Override
    protected String getResultJspPath() {
        // Return the path to the JSP page to display the search result
        return "/idResult.jsp";
    }
}
