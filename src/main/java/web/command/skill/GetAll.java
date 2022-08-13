package web.command.skill;

import tables.skill.Skill;
import tables.skill.SkillService;

import java.util.List;
import java.util.Map;

public class GetAll {
    public Map<String, Object> getAll(SkillService connections, Map<String, Object> params) {
        List<Skill> skills = connections.getAll();
        params.replace("skills", skills == null ? "" : skills);
        return params;
    }
}