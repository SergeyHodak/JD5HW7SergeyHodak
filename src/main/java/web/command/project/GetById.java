package web.command.project;

import tables.developer.Developer;
import tables.project.Project;
import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetById {
    public Map<String, Object> getById(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String id = req.getParameter("setId");
        Format format = new Format();
        String error = "";
        try {
            Project project = connections.getById(Long.parseLong(id));
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
        params.replace("project", format.getId() == 0 ? "" : format);
        params.replace("errorGetById", error);
        return params;
    }
}