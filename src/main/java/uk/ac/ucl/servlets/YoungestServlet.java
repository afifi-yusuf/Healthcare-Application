package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;
//Servlet for searching for the youngest person
@WebServlet("/findYoungestPerson.html")
public class YoungestServlet extends BaseYoungestOldestServlet {
    @Override
    protected String findPerson(Model model) {
        return model.findYoungestPerson();
    }

    @Override
    protected String getJspPath() {
        return "/searchYoungest.jsp";
    }
}
