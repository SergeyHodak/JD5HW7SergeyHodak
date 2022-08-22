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

public class CompanyUpdate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setId = req.getParameter("updateId");
            String setName = req.getParameter("updateName");
            String setDescription = req.getParameter("updateDescription");
            try {
                Company company = new Company(setName, setDescription);
                company.setId(Long.parseLong(setId));
                error = HibernateCompanyService.getInstance().update(company);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("errorUpdate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("company/company-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}