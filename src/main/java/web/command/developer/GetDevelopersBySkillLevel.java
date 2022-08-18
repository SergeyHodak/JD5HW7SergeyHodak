package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class GetDevelopersBySkillLevel {
    public Map<String, Object> get(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
        String setSkillLevel = req.getParameter("setSkillLevel");
        List<Developer> developers = connections.getDevelopersBySkillLevel(setSkillLevel);
        params.replace("developers", developers == null ? "" : developers);
        return params;
    }
}