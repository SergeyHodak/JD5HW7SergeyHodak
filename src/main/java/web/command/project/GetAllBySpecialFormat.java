package web.command.project;

import tables.project.ProjectService;
import tables.project.ProjectSpecialFormat;

import java.util.List;
import java.util.Map;

public class GetAllBySpecialFormat {
    public Map<String, Object> getAll(ProjectService connections, Map<String, Object> params) {
        List<ProjectSpecialFormat> projects = connections.getAllBySpecialFormat();
        params.replace("projectsSpecialFormat", projects.size() == 0 ? "" : projects);
        return params;
    }
}