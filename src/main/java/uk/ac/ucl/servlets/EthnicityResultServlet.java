package uk.ac.ucl.servlets;

import uk.ac.ucl.model.ModelFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/runethnicitysearch.html")
public class EthnicityResultServlet extends BaseSearchServlet {
    @Override
    protected String getSearchResult(HttpServletRequest request) throws IOException {
        return ModelFactory.getModel().getPeopleInEthnicity(request.getParameter("searchstring"));
    }

    @Override
    protected String getResultJspPath() {
        return "/ethnicityResult.jsp";
    }
}
