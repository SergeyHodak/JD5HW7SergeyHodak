package web.command.customer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.customer.Customer;
import tables.customer.HibernateCustomerService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerGetAll implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<Customer> customers = null;
        String method = req.getMethod();
        if (method.equals("POST")) {
            customers = HibernateCustomerService.getInstance().getAll();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customers", customers == null ? "" : customers);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("customer/customer-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}