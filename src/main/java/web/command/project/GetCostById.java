package web.command.project;

import lombok.Data;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.project.HibernateProjectService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetCostById implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        CostFormat result = new CostFormat();
        String method = req.getMethod();
        if (method.equals("POST")) {
            String id = req.getParameter("getCostById");
            try {
                long projectId = Long.parseLong(id);
                double costById = HibernateProjectService.getInstance().getCostById(projectId);
                result.setCost(costById);
                result.setId(projectId);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("costById", result.getId() == 0 ? "" : result);
        params.put("errorGetById", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("project/project-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }

    @Data
    private static class CostFormat {
        private long id;
        private double cost;
    }
}