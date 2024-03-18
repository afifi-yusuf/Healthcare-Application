package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/managepatients.html")
public class ManagePatientsServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/managePatients.jsp";
    }
}
