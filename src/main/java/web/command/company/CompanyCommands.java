package web.command.company;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.company.CompanyService;
import tables.company.HibernateCompanyService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyCommands implements Command {
    private static Map<String, Object> params = new HashMap<>();
    private static final CompanyService connections = HibernateCompanyService.getInstance();
    private static final GetAll getAll = new GetAll();
    private static final Create create = new Create();
    private static final GetById getById = new GetById();
    private static final Update update = new Update();
    private static final DeleteById deleteById = new DeleteById();

    static {
        params.put("companies", "");
        params.put("companyId", "");
        params.put("errorCreate", "");
        params.put("company", "");
        params.put("errorGetById", "");
        params.put("errorUpdate", "");
        params.put("errorDeleteById", "");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")) {
            if (req.getParameter("setName") != null) {
                params = create.create(req, connections, params);
            } else if (req.getParameter("setId") != null) {
                params = getById.getById(req, connections, params);
            } else if (req.getParameter("updateId") != null) {
                params = update.update(req, connections, params);
            } else if (req.getParameter("deleteId") != null) {
                params = deleteById.deleteById(req, connections, params);
            } else {
                params = getAll.getAll(connections, params);
            }
        }

        resp.setContentType("text/html");

        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}