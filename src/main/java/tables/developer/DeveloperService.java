package tables.developer;

import java.util.List;

public interface DeveloperService {
    long create(Developer developer, long[] skillIds);
    Developer getById(long id);
    List<Developer> getAll();
    String update(Developer developer, long[] skillIds);
    String deleteById(long id);
    List<Developer> getDevelopersByDepartment(String department);
    List<Developer> getDevelopersBySkillLevel(String skillLevel);
}