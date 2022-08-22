package web.command.company;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.company.Company;
import tables.company.HibernateCompanyService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyCreate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String id = "";
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setName = req.getParameter("setName");
            String setDescription = req.getParameter("setDescription");
            try {
                Company company = new Company(setName, setDescription);
                HibernateCompanyService.getInstance().create(company);
                id = String.valueOf(company.getId());
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", id);
        params.put("errorCreate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("company/company-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}