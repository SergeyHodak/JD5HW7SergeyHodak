package web.command.skill;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.skill.HibernateSkillService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkillDeleteById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("deleteId");
            try {
                error = HibernateSkillService.getInstance().deleteById(Long.parseLong(id));
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("errorDeleteById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("skill/skill-delete-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}