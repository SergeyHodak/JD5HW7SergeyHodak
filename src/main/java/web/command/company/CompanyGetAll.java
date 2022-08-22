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
import java.util.List;
import java.util.Map;

public class CompanyGetAll implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<Company> companies = null;
        String method = req.getMethod();
        if (method.equals("POST")) {
            companies = HibernateCompanyService.getInstance().getAll();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("companies", companies == null ? "" : companies);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("company/company-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}