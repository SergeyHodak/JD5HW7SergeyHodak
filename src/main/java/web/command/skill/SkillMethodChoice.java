package web.command.skill;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class SkillMethodChoice implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Collections.emptyMap()
        );
        engine.process("skill/skill", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}