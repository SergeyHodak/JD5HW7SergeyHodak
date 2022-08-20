package web.command.project;

import tables.project.Project;
import tables.project.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

public class Update {
    public Map<String, Object> update(HttpServletRequest req, ProjectService connections, Map<String, Object> params) {
        String setId = req.getParameter("updateId");
        String setName = req.getParameter("updateName");
        String setCompany = req.getParameter("updateCompany");
        String setCustomer = req.getParameter("updateCustomer");
        String setCreationDate = req.getParameter("updateCreationDate");
        String setDevelopers = req.getParameter("updateDevelopers");
        String error = "";
        try {
            Project project = new Project();
            project.setId(Long.parseLong(setId));
            project.setName(setName);
            long companyId = Long.parseLong(setCompany);
            long customerId = Long.parseLong(setCustomer);
            project.setCreationDate(LocalDate.parse(setCreationDate));
            String[] values = setDevelopers.split(",");
            long[] developerIds = new long[values.length];
            for (int i = 0; i < developerIds.length; i++) {
                developerIds[i] = Long.parseLong(values[i].strip());
            }
            error = connections.update(project, companyId, customerId, developerIds);
        } catch (Exception e) {
            error = e.getMessage();
        }
        params.replace("errorUpdate", error);
        return params;
    }
}