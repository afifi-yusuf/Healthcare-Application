package uk.ac.ucl.servlets;

import uk.ac.ucl.model.ModelFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//Servley for searching for people in a state
@WebServlet("/runstatesearch.html")
public class StateResultServlet extends BaseSearchServlet {
    @Override
    protected String getSearchResult(HttpServletRequest request) throws IOException {
        return ModelFactory.getModel().getPeopleInState(request.getParameter("searchstring"));
    }

    @Override
    protected String getResultJspPath() {
        return "/stateResult.jsp";
    }
}
