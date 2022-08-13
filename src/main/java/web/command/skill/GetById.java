package web.command.skill;

import tables.skill.Skill;
import tables.skill.SkillService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetById {
    public Map<String, Object> getById(HttpServletRequest req, SkillService connections, Map<String, Object> params) {
        String id = req.getParameter("setId");
        Skill skill = null;
        String error = "";

        try {
            skill = connections.getById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("skill", skill == null ? "" : skill);
        params.replace("errorGetById", error);

        return params;
    }
}