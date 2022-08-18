package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetById {
    public Map<String, Object> getById(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
        String id = req.getParameter("setId");
        Developer developer = null;
        String error = "";

        try {
            developer = connections.getById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("developer", developer == null ? "" : developer);
        params.replace("errorGetById", error);

        return params;
    }
}