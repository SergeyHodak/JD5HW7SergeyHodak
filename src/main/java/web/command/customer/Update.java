package web.command.customer;

import tables.customer.Customer;
import tables.customer.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Update {
    public Map<String, Object> update(HttpServletRequest req, CustomerService connections, Map<String, Object> params) {
        String setId = req.getParameter("updateId");
        String setFirstName = req.getParameter("updateFirstName");
        String setSecondName = req.getParameter("updateSecondName");
        String setAge = req.getParameter("updateAge");

        String error = "";

        try {
            Customer customer = new Customer();
            customer.setId(Long.parseLong(setId));
            customer.setFirstName(setFirstName);
            customer.setSecondName(setSecondName);
            customer.setAge(Integer.parseInt(setAge));
            error = connections.update(customer);
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorUpdate", error);

        return params;
    }
}