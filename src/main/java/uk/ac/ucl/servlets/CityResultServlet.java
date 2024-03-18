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

@WebServlet("/runcitysearch.html")
public class CityResultServlet extends BaseSearchServlet {
    @Override
    protected String getSearchResult(HttpServletRequest request) throws IOException {
        return ModelFactory.getModel().getPeopleInCity(request.getParameter("searchstring"));
    }

    @Override
    protected String getResultJspPath() {
        return "/cityResult.jsp";
    }
}



