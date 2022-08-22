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
import java.util.Map;

public class CustomerGetById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        Customer customer = null;
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("setId");
            try {
                customer = HibernateCustomerService.getInstance().getById(Long.parseLong(id));
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer == null ? "" : customer);
        params.put("errorGetById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("customer/customer-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}