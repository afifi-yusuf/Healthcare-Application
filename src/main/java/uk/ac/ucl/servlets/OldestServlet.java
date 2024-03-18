package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;

@WebServlet("/findOldestPerson.html")
public class OldestServlet extends BaseYoungestOldestServlet {
    @Override
    protected String findPerson(Model model) {
        return model.findOldestPerson();
    }

    @Override
    protected String getJspPath() {
        return "/searchOldest.jsp";
    }
}
