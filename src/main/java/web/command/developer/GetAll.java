package web.command.developer;

import tables.developer.Developer;
import tables.developer.DeveloperService;

import java.util.List;
import java.util.Map;

public class GetAll {
    public Map<String, Object> getAll(DeveloperService connections, Map<String, Object> params) {
        List<Developer> developers = connections.getAll();
        params.replace("developers", developers == null ? "" : developers);
        return params;
    }
}