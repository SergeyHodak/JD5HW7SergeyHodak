package web.command.project;

import tables.developer.Developer;
import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetDevelopersByProjectId {
    public Map<String, Object> getDevelopersByProjectId(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String id = req.getParameter("getDevelopersByProjectId");
        String error = "";
        Set<Developer> result = new HashSet<>();
        long projectId = 0;
        try {
            projectId = Long.parseLong(id);
            result = connections.getDevelopersByProjectId(projectId);
        } catch (Exception e) {
            error = e.getMessage();
        }
        params.replace("developers", result.size() == 0 ? "" : result);
        params.replace("pos", projectId);
        params.replace("errorGetById", error);
        return params;
    }
}