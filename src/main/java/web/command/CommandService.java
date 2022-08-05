package web.command;

import org.thymeleaf.TemplateEngine;
import web.command.company.CompanyCommand;
import web.command.company.GetAllCompanyCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private final Map<String, Command> commands;

    public CommandService() {
        commands = new HashMap<>();

        commands.put("GET /", new TablesCommand());
        commands.put("GET /company", new CompanyCommand());
        commands.put("GET /company/get-all", new GetAllCompanyCommand());
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;
        commands.get(commandKey).process(req, resp, engine);
    }
}