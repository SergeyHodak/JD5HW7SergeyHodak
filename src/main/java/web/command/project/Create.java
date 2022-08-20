package web.command.project;

import tables.project.Project;
import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

public class Create {
    public Map<String, Object> create(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String setName = req.getParameter("setName");
        String setCompany = req.getParameter("setCompany");
        String setCustomer = req.getParameter("setCustomer");
        String setCreationDate = req.getParameter("setCreationDate");
        String setDevelopers = req.getParameter("setDevelopers");

        String id = "";
        String error = "";

        try {
            Project project = new Project();
            project.setName(setName);
            long companyId = Long.parseLong(setCompany);
            long customerId = Long.parseLong(setCustomer);
            project.setCreationDate(LocalDate.parse(setCreationDate));
            String[] values = setDevelopers.split(",");
            long[] developerIds = new long[values.length];
            for (int i = 0; i < developerIds.length; i++) {
                developerIds[i] = Long.parseLong(values[i].strip());
            }
            connections.create(project, companyId, customerId, developerIds);
            id = String.valueOf(project.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("projectId", id);
        params.replace("errorCreate", error);

        return params;
    }
}