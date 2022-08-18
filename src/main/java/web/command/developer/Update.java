package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Update {
    public Map<String, Object> update(HttpServletRequest req, DeveloperService connections, Map<String, Object> params) {
        String setId = req.getParameter("updateId");
        String setFirstName = req.getParameter("updateFirstName");
        String setSecondName = req.getParameter("updateSecondName");
        String setAge = req.getParameter("updateAge");
        String setGender = req.getParameter("updateGender");
        String setSalary = req.getParameter("updateSalary");
        String setSkills = req.getParameter("updateSkills");

        String error = "";

        try {
            Developer developer = new Developer();
            developer.setId(Long.parseLong(setId));
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

            error = connections.update(developer, skills);
        } catch (Exception e) {
            error = e.getMessage();
        }

        params.replace("errorUpdate", error);

        return params;
    }
}