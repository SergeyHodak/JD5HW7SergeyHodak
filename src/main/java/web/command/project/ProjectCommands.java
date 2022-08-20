package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.project.HibernateProjectService;
import tables.project.ProjectService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectCommands implements Command {
    private static Map<String, Object> params = new HashMap<>();
    private static final ProjectService connections = HibernateProjectService.getInstance();
    private static final Create create = new Create();
    private static final GetById getById = new GetById();
    private static final GetCostById getCostById = new GetCostById();
    private static final GetDevelopersByProjectId getDevelopersByProjectId = new GetDevelopersByProjectId();
    private static final GetAll getAll = new GetAll();
    private static final GetAllBySpecialFormat getAllBySpecialFormat = new GetAllBySpecialFormat();
    private static final Update update = new Update();
    private static final DeleteById deleteById = new DeleteById();

    static {
        params.put("projectId", "");
        params.put("errorCreate", "");
        params.put("project", "");
        params.put("costById", "");
        params.put("developers", "");
        params.put("pos", "");
        params.put("errorGetById", "");
        params.put("projects", "");
        params.put("projectsSpecialFormat", "");
        params.put("errorUpdate", "");
        params.put("errorDeleteById", "");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        for (String key : params.keySet()) {
            params.replace(key, "");
        }
        String method = req.getMethod();
        if (method.equals("POST")) {
            if (req.getParameter("setName") != null) {
                params = create.create(req, connections, params);
            } else if (req.getParameter("setId") != null) {
                params = getById.getById(req, connections, params);
            } else if (req.getParameter("getCostById") != null) {
                params = getCostById.getCostById(req, connections, params);
            } else if (req.getParameter("getDevelopersByProjectId") != null) {
                params = getDevelopersByProjectId.getDevelopersByProjectId(req, connections, params);
            } else if (req.getParameter("specialFormat") != null) {
                params = getAllBySpecialFormat.getAll(connections, params);
            } else if (req.getParameter("updateId") != null) {
                params = update.update(req, connections, params);
            } else if (req.getParameter("deleteId") != null) {
                params = deleteById.deleteById(req, connections, params);
            }
            else {
                params = getAll.getAll(connections, params);
            }
        }

        resp.setContentType("text/html");

        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("project", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}