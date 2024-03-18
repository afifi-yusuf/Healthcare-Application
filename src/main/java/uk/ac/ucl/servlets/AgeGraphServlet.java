package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;

@WebServlet("/agegraph.html")
public class AgeGraphServlet extends BaseGraphServlet {
    @Override
    protected void createGraph(Model model) {
        model.createAgeDistributionGraph("webapp/images/ageDistribution.png");
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
