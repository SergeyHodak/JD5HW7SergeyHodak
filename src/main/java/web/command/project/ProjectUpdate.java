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

public class ProjectUpdate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setId = req.getParameter("updateId");
            String setName = req.getParameter("updateName");
            String setCompany = req.getParameter("updateCompany");
            String setCustomer = req.getParameter("updateCustomer");
            String setCreationDate = req.getParameter("updateCreationDate");
            String setDevelopers = req.getParameter("updateDevelopers");
            try {
                long id = Long.parseLong(setId);
                long companyId = Long.parseLong(setCompany);
                long customerId = Long.parseLong(setCustomer);
                LocalDate creationDate = LocalDate.parse(setCreationDate);
                String[] values = setDevelopers.split(",");
                long[] developerIds = new long[values.length];
                for (int i = 0; i < developerIds.length; i++) {
                    developerIds[i] = Long.parseLong(values[i].strip());
                }
                error = HibernateProjectService.getInstance()
                        .update(id, setName, companyId, customerId, creationDate, developerIds);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("errorUpdate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}