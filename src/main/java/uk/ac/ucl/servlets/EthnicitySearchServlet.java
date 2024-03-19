package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/searchEthnicity.html")
public class EthnicitySearchServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/ethnicitySearch.jsp";
    }
}
