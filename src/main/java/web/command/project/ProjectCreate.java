package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.project.HibernateProjectService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProjectCreate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String id = "";
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setName = req.getParameter("setName");
            String setCompany = req.getParameter("setCompany");
            String setCustomer = req.getParameter("setCustomer");
            String setCreationDate = req.getParameter("setCreationDate");
            String setDevelopers = req.getParameter("setDevelopers");
            try {
                long companyId = Long.parseLong(setCompany);
                long customerId = Long.parseLong(setCustomer);
                LocalDate creationDate = LocalDate.parse(setCreationDate);
                String[] values = setDevelopers.split(",");
                long[] developerIds = new long[values.length];
                for (int i = 0; i < developerIds.length; i++) {
                    developerIds[i] = Long.parseLong(values[i].strip());
                }
                long result = HibernateProjectService.getInstance()
                        .create(setName, companyId, customerId, creationDate, developerIds);
                id = String.valueOf(result);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("projectId", id);
        params.put("errorCreate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}