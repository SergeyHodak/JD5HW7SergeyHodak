package tables.project;

import tables.developer.Developer;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProjectService {
    long create(String name, long companyId, long customerId, LocalDate creationDate, long[] developerIds);
    Project getById(long id);
    double getCostById(long id);
    Set<Developer> getDevelopersByProjectId(long id);
    List<Project> getAll();
    List<ProjectSpecialFormat> getAllBySpecialFormat();
    String update(long id, String name, long companyId, long customerId, LocalDate creationDate, long[] developerIds);
    String deleteById(long id);
}