package web.command.customer;

import tables.customer.Customer;
import tables.customer.CustomerService;

import java.util.List;
import java.util.Map;

public class GetAll {
    public Map<String, Object> getAll(CustomerService connections, Map<String, Object> params) {
        List<Customer> customers = connections.getAll();
        params.replace("customers", customers == null ? "" : customers);
        return params;
    }
}