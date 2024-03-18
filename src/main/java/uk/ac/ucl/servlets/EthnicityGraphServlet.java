package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ethnicitygraph.html")
public class EthnicityGraphServlet extends BaseGraphServlet {
    @Override
    protected void createGraph(Model model) {
        model.createEthnicityDistributionGraph("webapp/images/ethnicityDistribution.png");
    }

    @Override
    protected String getResultMessage() {
        return "Ethnicity distribution graph created";
    }

    @Override
    protected String getJspPath() {
        return "/ethnicityGraph.jsp";
    }
}
