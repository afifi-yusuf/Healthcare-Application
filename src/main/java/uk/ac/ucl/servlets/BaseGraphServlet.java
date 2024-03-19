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
//Abstract class for creating a graph
@WebServlet("/baseGraph.html")
public abstract class BaseGraphServlet extends HttpServlet {
    protected abstract void createGraph(Model model);

    protected abstract String getResultMessage();

    protected abstract String getJspPath();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        createGraph(model);
        request.setAttribute("result", getResultMessage());

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(getJspPath());
        dispatch.forward(request, response);
    }
}
