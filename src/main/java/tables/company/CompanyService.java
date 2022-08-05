package tables.company;

import java.util.List;

public interface CompanyService {
    long create(Company company);
    Company getById(long id);
    List<Company> getAll();
    void update(Company company);
    void deleteById(long id);
}