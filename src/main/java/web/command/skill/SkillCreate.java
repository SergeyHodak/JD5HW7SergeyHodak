package web.command.skill;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.skill.HibernateSkillService;
import tables.skill.Skill;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkillCreate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String id = "";
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setDepartment = req.getParameter("setDepartment");
            String setSkillLevel = req.getParameter("setSkillLevel");
            try {
                Skill skill = new Skill(setDepartment, setSkillLevel);
                HibernateSkillService.getInstance().create(skill);
                id = String.valueOf(skill.getId());
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("skillId", id);
        params.put("errorCreate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("skill/skill-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}