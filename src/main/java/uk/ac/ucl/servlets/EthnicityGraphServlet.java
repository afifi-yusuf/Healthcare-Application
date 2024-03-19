package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;
//Servlet for creating ethnicity distribution graph
@WebServlet("/ethnicitygraph.html")
public class EthnicityGraphServlet extends BaseGraphServlet {
    @Override
    protected void createGraph(Model model) {
        model.createEthnicityDistributionGraph("src/main/webapp/images/ethnicityDistribution.png");
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
