package web.command.developer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.developer.DeveloperService;
import tables.developer.HibernateDeveloperService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeveloperCommands implements Command {
    private static Map<String, Object> params = new HashMap<>();
    private static final DeveloperService connections = HibernateDeveloperService.getInstance();
    private static final GetAll getAll = new GetAll();
    private static final Create create = new Create();
    private static final GetById getById = new GetById();
    private static final Update update = new Update();
    private static final GetDevelopersByDepartment getDevelopersByDepartment = new GetDevelopersByDepartment();
    private static final GetDevelopersBySkillLevel getDevelopersBySkillLevel = new GetDevelopersBySkillLevel();
    private static final DeleteById deleteById = new DeleteById();

    static {
        params.put("developers", "");
        params.put("developerId", "");
        params.put("errorCreate", "");
        params.put("developer", "");
        params.put("errorGetById", "");
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
            if (req.getParameter("setFirstName") != null) {
                params = create.create(req, connections, params);
            } else if (req.getParameter("setId") != null) {
                params = getById.getById(req, connections, params);
            } else if (req.getParameter("setDepartment") != null) {
                params = getDevelopersByDepartment.get(req, connections, params);
            } else if (req.getParameter("setSkillLevel") != null) {
                params = getDevelopersBySkillLevel.get(req, connections, params);
            } else if (req.getParameter("updateId") != null) {
                params = update.update(req, connections, params);
            } else if (req.getParameter("deleteId") != null) {
                params = deleteById.deleteById(req, connections, params);
            } else {
                params = getAll.getAll(connections, params);
            }
        }

        resp.setContentType("text/html");

        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}