package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.developer.Developer;
import tables.project.HibernateProjectService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetDevelopersByProjectId implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        Set<Developer> result = new HashSet<>();
        long projectId = 0;
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("getDevelopersByProjectId");
            try {
                projectId = Long.parseLong(id);
                result = HibernateProjectService.getInstance().getDevelopersByProjectId(projectId);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("developers", result.size() == 0 ? "" : result);
        params.put("pos", projectId);
        params.put("errorGetById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}