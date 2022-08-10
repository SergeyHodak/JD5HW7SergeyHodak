package web.command.company;

import tables.company.Company;
import tables.company.CompanyService;

import java.util.List;
import java.util.Map;

public class GetAll {
    public Map<String, Object> getAll(CompanyService connections, Map<String, Object> params) {
        List<Company> companies = connections.getAll();
        params.replace("companies", companies == null ? "" : companies);
        return params;
    }
}