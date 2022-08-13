package web.command.customer;

import tables.customer.Customer;
import tables.customer.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetById {
    public Map<String, Object> getById(HttpServletRequest req, CustomerService connections, Map<String, Object> params) {
        String id = req.getParameter("setId");
        Customer customer = null;
        String error = "";

        try {
            customer = connections.getById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("customer", customer == null ? "" : customer);
        params.replace("errorGetById", error);

        return params;
    }
}