package web.command.company;

import tables.company.Company;
import tables.company.CompanyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetById {
    public Map<String, Object> getById(HttpServletRequest req, CompanyService connections, Map<String, Object> params) {
        String id = req.getParameter("setId");
        Company company = null;
        String error = "";

        try {
            company = connections.getById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("company", company == null ? "" : company);
        params.replace("errorGetById", error);

        return params;
    }
}