package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/editPatient.html")
public class EditPatientServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/editPatient.jsp";
    }
}
