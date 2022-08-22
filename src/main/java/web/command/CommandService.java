package web.command;

import org.thymeleaf.TemplateEngine;
import web.command.company.*;
import web.command.customer.*;
import web.command.developer.*;
import web.command.project.*;
import web.command.skill.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private final Map<String, Command> commands;

    public CommandService() {
        commands = new HashMap<>();

        commands.put("/", new TablesCommand());

        commands.put("/company", new CompanyMethodChoice());
        commands.put("/company-create", new CompanyCreate());
        commands.put("/company-get-by-id", new CompanyGetById());
        commands.put("/company-get-all", new CompanyGetAll());
        commands.put("/company-update", new CompanyUpdate());
        commands.put("/company-delete-by-id", new CompanyDeleteById());

        commands.put("/customer", new CustomerMethodChoice());
        commands.put("/customer-create", new CustomerCreate());
        commands.put("/customer-get-by-id", new CustomerGetById());
        commands.put("/customer-get-all", new CustomerGetAll());
        commands.put("/customer-update", new CustomerUpdate());
        commands.put("/customer-delete-by-id", new CustomerDeleteById());

        commands.put("/skill", new SkillMethodChoice());
        commands.put("/skill-create", new SkillCreate());
        commands.put("/skill-get-by-id", new SkillGetById());
        commands.put("/skill-get-all", new SkillGetAll());
        commands.put("/skill-update", new SkillUpdate());
        commands.put("/skill-delete-by-id", new SkillDeleteById());

        commands.put("/developer", new DeveloperMethodChoice());
        commands.put("/developer-create", new DeveloperCreate());
        commands.put("/developer-get-by-id", new DeveloperGetById());
        commands.put("/developer-get-all", new DeveloperGetAll());
        commands.put("/get-developers-by-department", new GetDevelopersByDepartment());
        commands.put("/get-developers-by-skill-level", new GetDevelopersBySkillLevel());
        commands.put("/developer-update", new DeveloperUpdate());
        commands.put("/developer-delete-by-id", new DeveloperDeleteById());

        commands.put("/project", new ProjectMethodChoice());
        commands.put("/project-create", new ProjectCreate());
        commands.put("/project-get-by-id", new ProjectGetById());
        commands.put("/get-cost-by-id", new GetCostById());
        commands.put("/get-developers-by-project-id", new GetDevelopersByProjectId());
        commands.put("/project-get-all", new ProjectGetAll());
        commands.put("/get-all-by-special-format", new GetAllBySpecialFormat());
        commands.put("/project-update", new ProjectUpdate());
        commands.put("/project-delete-by-id", new ProjectDeleteById());
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        commands.get(req.getRequestURI()).process(req, resp, engine);
    }
}