package uk.ac.ucl.servlets;

import javax.servlet.annotation.WebServlet;
//Servlet for addi patient page
@WebServlet("/addPatient.html")
public class AddPatientServlet extends BaseWebServlet {
    @Override
    protected String getJspPath() {
        return "/addPatient.jsp";
    }
}
