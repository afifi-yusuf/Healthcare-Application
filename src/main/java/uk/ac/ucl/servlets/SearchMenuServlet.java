package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for search menu page
@WebServlet("/searchmenu.html")
public class SearchMenuServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/searchMenu.jsp";
    }
}
