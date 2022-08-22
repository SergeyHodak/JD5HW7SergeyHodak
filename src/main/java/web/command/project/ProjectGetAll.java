package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.developer.Developer;
import tables.project.HibernateProjectService;
import tables.project.Project;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectGetAll implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        List<Format> result = new ArrayList<>();
        String method = req.getMethod();
        if (method.equals("POST")) {
            List<Project> projects = HibernateProjectService.getInstance().getAll();
            for (Project project : projects) {
                Format format = new Format();
                format.setId(project.getId());
                format.setName(project.getName());
                format.setCompanyId(project.getCompany().getId());
                format.setCustomerId(project.getCustomer().getId());
                format.setCost(project.getCost());
                format.setCreationDate(project.getCreationDate());
                List<Long> developerIds = new ArrayList<>();
                for (Developer developer : project.getDevelopers()) {
                    developerIds.add(developer.getId());
                }
                format.setDevelopers(developerIds);
                result.add(format);
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("projectsSpecialFormat", "");
        params.put("projects", result.size() == 0 ? "" : result);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}