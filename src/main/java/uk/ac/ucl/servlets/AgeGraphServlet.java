package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;
//Servlet for age graph page
@WebServlet("/agegraph.html")
public class AgeGraphServlet extends BaseGraphServlet {
    @Override
    protected void createGraph(Model model) {
        model.createAgeDistributionGraph("src/main/webapp/images/ageDistribution.png");
    }

    @Override
    protected String getResultMessage() {
        return "Age distribution graph created";
    }

    @Override
    protected String getJspPath() {
        return "/ageGraph.jsp";
    }
}
