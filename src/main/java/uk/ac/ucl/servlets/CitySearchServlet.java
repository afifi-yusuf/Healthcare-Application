package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for search city page
@WebServlet("/searchCity.html")
public class CitySearchServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/citySearch.jsp";
    }
}
