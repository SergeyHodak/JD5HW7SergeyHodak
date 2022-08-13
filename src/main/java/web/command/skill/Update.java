package web.command.skill;

import tables.skill.Skill;
import tables.skill.SkillService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Update {
    public Map<String, Object> update(HttpServletRequest req, SkillService connections, Map<String, Object> params) {
        String setId = req.getParameter("updateId");
        String updateDepartment = req.getParameter("updateDepartment");
        String updateSkillLevel = req.getParameter("updateSkillLevel");

        String error = "";

        try {
            Skill skill = new Skill();
            skill.setId(Long.parseLong(setId));
            skill.setDepartment(updateDepartment);
            skill.setSkillLevel(updateSkillLevel);
            error = connections.update(skill);
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorUpdate", error);

        return params;
    }
}