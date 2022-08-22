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
import java.util.Map;

public class DeveloperGetById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        Developer developer = null;
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("setId");
            try {
                developer = HibernateDeveloperService.getInstance().getById(Long.parseLong(id));
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("developer", developer == null ? "" : developer);
        params.put("errorGetById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("developer/developer-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}