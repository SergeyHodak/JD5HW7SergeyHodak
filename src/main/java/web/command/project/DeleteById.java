package web.command.project;

import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteById {
    public Map<String, Object> deleteById(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String id = req.getParameter("deleteId");

        String error = "";

        try {
            error = connections.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorDeleteById", error);

        return params;
    }
}