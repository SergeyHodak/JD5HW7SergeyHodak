package web.command;

import org.thymeleaf.TemplateEngine;
import web.command.company.CompanyCommands;
import web.command.customer.CustomerCommands;
import web.command.skill.SkillCommands;

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
        commands.put("/company", new CompanyCommands());
        commands.put("/customer", new CustomerCommands());
        commands.put("/skill", new SkillCommands());
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        commands.get(req.getRequestURI()).process(req, resp, engine);
    }
}