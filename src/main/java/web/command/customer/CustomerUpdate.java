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

public class CustomerUpdate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setId = req.getParameter("updateId");
            String setFirstName = req.getParameter("updateFirstName");
            String setSecondName = req.getParameter("updateSecondName");
            String setAge = req.getParameter("updateAge");
            try {
                int age = Integer.parseInt(setAge);
                Customer customer = new Customer(setFirstName, setSecondName, age);
                customer.setId(Long.parseLong(setId));
                error = HibernateCustomerService.getInstance().update(customer);
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
        engine.process("customer/customer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}