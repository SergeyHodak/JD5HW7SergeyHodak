package web.command.customer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.customer.CustomerService;
import tables.customer.HibernateCustomerService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerCommands implements Command {
    private static Map<String, Object> params = new HashMap<>();
    private static final CustomerService connections = HibernateCustomerService.getInstance();
    private static final GetAll getAll = new GetAll();
    private static final Create create = new Create();
    private static final GetById getById = new GetById();
    private static final Update update = new Update();
    private static final DeleteById deleteById = new DeleteById();

    static {
        params.put("customers", "");
        params.put("customerId", "");
        params.put("errorCreate", "");
        params.put("customer", "");
        params.put("errorGetById", "");
        params.put("errorUpdate", "");
        params.put("errorDeleteById", "");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")) {
            if (req.getParameter("setFirstName") != null) {
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

        engine.process("customer", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}