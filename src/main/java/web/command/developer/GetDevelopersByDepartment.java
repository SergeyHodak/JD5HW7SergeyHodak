package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class GetDevelopersByDepartment {
    public Map<String, Object> get(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
        String setDepartment = req.getParameter("setDepartment");
        List<Developer> developers = connections.getDevelopersByDepartment(setDepartment);
        params.replace("developers", developers == null ? "" : developers);
        return params;
    }
}