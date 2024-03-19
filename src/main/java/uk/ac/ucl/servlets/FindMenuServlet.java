package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for find menu page
@WebServlet("/findmenu.html")
public class FindMenuServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/findMenu.jsp";
    }
}
