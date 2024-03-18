package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/searchState.html")
public class StateServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/stateSearch.jsp";
    }
}
