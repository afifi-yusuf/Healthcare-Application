package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for graph menu page
@WebServlet("/graphmenu.html")
public class GraphMenuServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/graphMenu.jsp";
    }
}
