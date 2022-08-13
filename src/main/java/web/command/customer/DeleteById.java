package web.command.customer;

import tables.customer.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DeleteById {
    public Map<String, Object> deleteById(HttpServletRequest req, CustomerService connections, Map<String, Object> params) {
        String id = req.getParameter("deleteId");

        String error = "";

        try {
            error = connections.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorDeleteById", error);

        return params;
    }
}