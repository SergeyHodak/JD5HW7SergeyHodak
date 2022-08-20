package web.command.project;

import lombok.Data;
import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetCostById {
    public Map<String, Object> getCostById(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String id = req.getParameter("getCostById");
        String error = "";
        CostFormat result = new CostFormat();
        try {
            long projectId = Long.parseLong(id);
            double costById = connections.getCostById(projectId);
            result.setCost(costById);
            result.setId(projectId);
        } catch (Exception e) {
            error = e.getMessage();
        }
        params.replace("costById", result.getId() == 0 ? "" : result);
        params.replace("errorGetById", error);
        return params;
    }

    @Data
    private static class CostFormat {
        private long id;
        private double cost;
    }
}