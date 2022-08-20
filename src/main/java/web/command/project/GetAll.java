package web.command.project;

import tables.developer.Developer;
import tables.project.Project;
import tables.project.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAll {
    public Map<String, Object> getAll(ProjectService connections, Map<String, Object> params) {
        List<Project> projects = connections.getAll();
        List<Format> result = new ArrayList<>();
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
        params.replace("projects", result.size() == 0 ? "" : result);
        return params;
    }
}