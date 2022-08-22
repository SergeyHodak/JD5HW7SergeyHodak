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

public class DeveloperUpdate implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String error = "";
        String method = req.getMethod();
        if (method.equals("POST")) {
            String setId = req.getParameter("updateId");
            String setFirstName = req.getParameter("updateFirstName");
            String setSecondName = req.getParameter("updateSecondName");
            String setAge = req.getParameter("updateAge");
            String setGender = req.getParameter("updateGender");
            String setSalary = req.getParameter("updateSalary");
            String setSkills = req.getParameter("updateSkills");
            try {
                int age = Integer.parseInt(setAge);
                Developer.Gender gender = Developer.Gender.valueOf(setGender);
                double salary = Double.parseDouble(setSalary);
                Developer developer = new Developer(setFirstName, setSecondName, age, gender, salary);
                developer.setId(Long.parseLong(setId));
                String[] values = setSkills.split(",");
                long[] skills = new long[values.length];
                for (int i = 0; i < skills.length; i++) {
                    skills[i] = Long.parseLong(values[i].strip());
                }
                error = HibernateDeveloperService.getInstance().update(developer, skills);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("errorUpdate", error);
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );
        engine.process("developer/developer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}