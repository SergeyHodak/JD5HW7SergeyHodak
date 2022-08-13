package tables.customer;

import java.util.List;

public interface CustomerService {
    long create(Customer customer);
    Customer getById(long id);
    List<Customer> getAll();
    String update(Customer customer);
    String deleteById(long id);
}