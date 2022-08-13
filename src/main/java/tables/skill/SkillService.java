package tables.skill;

import java.util.List;

public interface SkillService {
    long create(Skill skill);
    Skill getById(long id);
    List<Skill> getAll();
    String update(Skill skill);
    String deleteById(long id);
}