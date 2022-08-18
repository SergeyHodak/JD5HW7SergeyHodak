package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Create {
    public Map<String, Object> create(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
        String setFirstName = req.getParameter("setFirstName");
        String setSecondName = req.getParameter("setSecondName");
        String setAge = req.getParameter("setAge");
        String setGender = req.getParameter("setGender");
        String setSalary = req.getParameter("setSalary");
        String setSkills = req.getParameter("setSkills");

        String id = "";
        String error = "";

        try {
            Developer developer = new Developer();
            developer.setFirstName(setFirstName);
            developer.setSecondName(setSecondName);
            developer.setAge(Integer.parseInt(setAge));
            developer.setGender(Developer.Gender.valueOf(setGender));
            developer.setSalary(Double.parseDouble(setSalary));

            String[] values = setSkills.split(",");
            long[] skills = new long[values.length];
            for (int i = 0; i < skills.length; i++) {
                skills[i] = Long.parseLong(values[i].strip());
            }

            connections.create(developer, skills);
            id = String.valueOf(developer.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("developerId", id);
        params.replace("errorCreate", error);

        return params;
    }
}