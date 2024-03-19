package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for managing patients page
@WebServlet("/managepatients.html")
public class ManagePatientsServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/managePatients.jsp";
    }
}
