package web.command.company;

import tables.company.CompanyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteById {
    public Map<String, Object> deleteById(HttpServletRequest req, CompanyService connections, Map<String, Object> params) {
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