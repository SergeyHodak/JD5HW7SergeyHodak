package web.command.skill;

import tables.skill.Skill;
import tables.skill.SkillService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Create {
    public Map<String, Object> create(HttpServletRequest req, SkillService connections, Map<String, Object> params) {
        String setDepartment = req.getParameter("setDepartment");
        String setSkillLevel = req.getParameter("setSkillLevel");

        String id = "";
        String error = "";

        try {
            Skill skill = new Skill();
            skill.setDepartment(setDepartment);
            skill.setSkillLevel(setSkillLevel);
            connections.create(skill);
            id = String.valueOf(skill.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("skillId", id);
        params.replace("errorCreate", error);

        return params;
    }
}