package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/searchCity.html")
public class CityServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/citySearch.jsp";
    }
}
