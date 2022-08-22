package web.command.developer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.developer.Developer;
import tables.developer.HibernateDeveloperService;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class DeveloperCreate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String id = "";
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setFirstName = req.getParameter("setFirstName");
            String setSecondName = req.getParameter("setSecondName");
            String setAge = req.getParameter("setAge");
            String setGender = req.getParameter("setGender");
            String setSalary = req.getParameter("setSalary");
            String setSkills = req.getParameter("setSkills");
            try {
                int age = Integer.parseInt(setAge);
                Developer.Gender gender = Developer.Gender.valueOf(setGender);
                double salary = Double.parseDouble(setSalary);
                Developer developer = new Developer(setFirstName, setSecondName, age, gender, salary);
                String[] values = setSkills.split(",");
                long[] skills = new long[values.length];
                for (int i = 0; i < skills.length; i++) {
                    skills[i] = Long.parseLong(values[i].strip());
                }
                HibernateDeveloperService.getInstance().create(developer, skills);
                id = String.valueOf(developer.getId());
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("developerId", id);
        params.put("errorCreate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("developer/developer-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}