package tables.project;

import tables.developer.Developer;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    long create(Project project, long companyId, long customerId, long[] developerIds);
    Project getById(long id);
    double getCostById(long id);
    Set<Developer> getDevelopersByProjectId(long id);
    List<Project> getAll();
    List<ProjectSpecialFormat> getAllBySpecialFormat();
    String update(Project project, long companyId, long customerId, long[] developerIds);
    String deleteById(long id);
}