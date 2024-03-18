package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/findmenu.html")
public class FindMenuServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/findMenu.jsp";
    }
}
