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

public class CustomerCreate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String id = "";
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setFirstName = req.getParameter("setFirstName");
            String setSecondName = req.getParameter("setSecondName");
            String setAge = req.getParameter("setAge");
            try {
                int age = Integer.parseInt(setAge);
                Customer customer = new Customer(setFirstName, setSecondName, age);
                HibernateCustomerService.getInstance().create(customer);
                id = String.valueOf(customer.getId());
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", id);
        params.put("errorCreate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("customer/customer-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}