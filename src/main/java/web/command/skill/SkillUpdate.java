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

public class SkillUpdate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setId = req.getParameter("updateId");
            String updateDepartment = req.getParameter("updateDepartment");
            String updateSkillLevel = req.getParameter("updateSkillLevel");

            try {
                Skill skill = new Skill(updateDepartment, updateSkillLevel);
                skill.setId(Long.parseLong(setId));
                error = HibernateSkillService.getInstance().update(skill);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("errorUpdate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("skill/skill-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}