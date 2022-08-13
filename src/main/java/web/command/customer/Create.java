package web.command.customer;

import tables.customer.Customer;
import tables.customer.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Create {
    public Map<String, Object> create(HttpServletRequest req, CustomerService connections, Map<String, Object> params) {
        String setFirstName = req.getParameter("setFirstName");
        String setSecondName = req.getParameter("setSecondName");
        String setAge = req.getParameter("setAge");

        String id = "";
        String error = "";

        try {
            Customer customer = new Customer();
            customer.setFirstName(setFirstName);
            customer.setSecondName(setSecondName);
            customer.setAge(Integer.parseInt(setAge));
            connections.create(customer);
            id = String.valueOf(customer.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("customerId", id);
        params.replace("errorCreate", error);

        return params;
    }
}