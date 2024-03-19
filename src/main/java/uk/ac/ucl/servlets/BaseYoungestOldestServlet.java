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
//Abstract class for servlets that display the youngest and oldest person
@WebServlet("/baseYoungestOldest.html")
public abstract class BaseYoungestOldestServlet extends HttpServlet {
    protected abstract String findPerson(Model model);

    protected abstract String getJspPath();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String searchResult = findPerson(model);
        request.setAttribute("result", searchResult);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(getJspPath());
        dispatch.forward(request, response);
    }
}
