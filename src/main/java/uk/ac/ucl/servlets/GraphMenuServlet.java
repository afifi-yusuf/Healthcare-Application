package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/graphmenu.html")
public class GraphMenuServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/graphMenu.jsp";
    }
}
