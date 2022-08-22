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
import java.util.List;
import java.util.Map;

public class SkillGetAll implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<Skill> skills = null;
        String method = req.getMethod();
        if (method.equals("POST")) {
            skills = HibernateSkillService.getInstance().getAll();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("skills", skills == null ? "" : skills);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("skill/skill-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}