package web.command.skill;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.skill.HibernateSkillService;
import tables.skill.SkillService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkillCommands implements Command {
    private static Map<String, Object> params = new HashMap<>();
    private static final SkillService connections = HibernateSkillService.getInstance();
    private static final GetAll getAll = new GetAll();
    private static final Create create = new Create();
    private static final GetById getById = new GetById();
    private static final Update update = new Update();
    private static final DeleteById deleteById = new DeleteById();

    static {
        params.put("skills", "");
        params.put("skillId", "");
        params.put("errorCreate", "");
        params.put("skill", "");
        params.put("errorGetById", "");
        params.put("errorUpdate", "");
        params.put("errorDeleteById", "");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")) {
            if (req.getParameter("setDepartment") != null) {
                params = create.create(req, connections, params);
            } else if (req.getParameter("setId") != null) {
                params = getById.getById(req, connections, params);
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

        engine.process("skill", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}