package tables.company;

import java.util.List;

public interface CompanyService {
    long create(Company company);
    Company getById(long id);
    List<Company> getAll();
    String update(Company company);
    String deleteById(long id);
}