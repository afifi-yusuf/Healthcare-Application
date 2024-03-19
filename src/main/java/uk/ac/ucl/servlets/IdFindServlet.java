package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for id search page
@WebServlet("/idfind.html")
public class IdFindServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/idSearch.jsp";
    }
}
