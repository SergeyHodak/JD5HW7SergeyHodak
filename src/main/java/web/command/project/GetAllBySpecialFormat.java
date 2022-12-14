package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.project.HibernateProjectService;
import tables.project.ProjectSpecialFormat;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllBySpecialFormat implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<ProjectSpecialFormat> projects = new ArrayList<>();
        String method = req.getMethod();
        if (method.equals("POST")) {
            projects = HibernateProjectService.getInstance().getAllBySpecialFormat();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("projectsSpecialFormat", projects.size() == 0 ? "" : projects);
        params.put("projects", "");
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}