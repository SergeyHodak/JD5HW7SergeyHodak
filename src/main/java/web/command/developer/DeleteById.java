package web.command.developer;

import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteById {
    public Map<String, Object> deleteById(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
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