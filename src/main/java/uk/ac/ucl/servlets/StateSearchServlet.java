package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for state search page
@WebServlet("/searchState.html")
public class StateSearchServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/stateSearch.jsp";
    }
}
