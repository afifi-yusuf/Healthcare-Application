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
//Abstract class for searching
@WebServlet("/baseSearch.html")
public abstract class BaseSearchServlet extends HttpServlet {
    protected abstract String getSearchResult(HttpServletRequest request) throws IOException;

    protected abstract String getResultJspPath();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String searchResult = getSearchResult(request);
        request.setAttribute("result", searchResult);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(getResultJspPath());
        dispatch.forward(request, response);
    }
}
