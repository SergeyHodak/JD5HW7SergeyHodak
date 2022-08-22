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

public class ProjectGetById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        Format format = new Format();
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("setId");
            try {
                Project project = HibernateProjectService.getInstance().getById(Long.parseLong(id));
                if (project.getId() != 0) {
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
                }
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("project", format.getId() == 0 ? "" : format);
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