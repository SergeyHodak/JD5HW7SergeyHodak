package web.command.developer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.developer.Developer;
import tables.developer.HibernateDeveloperService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDevelopersByDepartment implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<Developer> developers = null;
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setDepartment = req.getParameter("setDepartment");
            developers = HibernateDeveloperService.getInstance().getDevelopersByDepartment(setDepartment);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("developers", developers == null ? "" : developers);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("developer/developer-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}