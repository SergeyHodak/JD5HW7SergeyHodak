package web.command.company;

import tables.company.Company;
import tables.company.CompanyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Update {
    public Map<String, Object> update(HttpServletRequest req, CompanyService connections, Map<String, Object> params) {
        String setId = req.getParameter("updateId");
        String setName = req.getParameter("updateName");
        String setDescription = req.getParameter("updateDescription");

        String error = "";

        try {
            Company company = new Company();
            company.setId(Integer.valueOf(setId));
            company.setName(setName);
            company.setDescription(setDescription);
            error = connections.update(company);
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorUpdate", error);

        return params;
    }
}