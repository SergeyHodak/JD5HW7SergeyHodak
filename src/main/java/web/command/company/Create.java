package web.command.company;

import tables.company.Company;
import tables.company.CompanyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Create {
    public Map<String, Object> create(HttpServletRequest req, CompanyService connections, Map<String, Object> params) {
        String setName = req.getParameter("setName");
        String setDescription = req.getParameter("setDescription");

        String id = "";
        String error = "";

        try {
            Company company = new Company();
            company.setName(setName);
            company.setDescription(setDescription);
            connections.create(company);
            id = String.valueOf(company.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("companyId", id);
        params.replace("errorCreate", error);

        return params;
    }
}