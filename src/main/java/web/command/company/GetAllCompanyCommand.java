package web.command.company;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.company.Company;
import tables.company.CompanyService;
import tables.company.HibernateCompanyService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllCompanyCommand implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        resp.setContentType("text/html");

        CompanyService connections = new HibernateCompanyService();

        List<Company> companies = connections.getAll();

        Map<String, Object> params = new HashMap<>();
        params.put("companies", companies);
        params.put("query", req.getParameter("query"));

        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}