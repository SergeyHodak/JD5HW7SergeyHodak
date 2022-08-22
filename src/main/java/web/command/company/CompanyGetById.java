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

public class CompanyGetById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        Company company = null;
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("setId");
            try {
                company = HibernateCompanyService.getInstance().getById(Long.parseLong(id));
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("company", company == null ? "" : company);
        params.put("errorGetById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("company/company-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}