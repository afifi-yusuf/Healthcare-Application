package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;

import javax.servlet.annotation.WebServlet;

@WebServlet("/gendergraph.html")
public class GenderGraphServlet extends BaseGraphServlet {
    @Override
    protected void createGraph(Model model) {
        model.createGenderDistributionPieChart("/Users/yusufafifi/Desktop/OOP/OOP_Coursework/src/main/webapp/images/genderDistribution.png");
    }

    @Override
    protected String getResultMessage() {
        return "Gender distribution graph created";
    }

    @Override
    protected String getJspPath() {
        return "/genderGraph.jsp";
    }
}
