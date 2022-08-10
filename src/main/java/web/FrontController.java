package web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import storage.DatabaseInitService;
import storage.StorageConstants;
import web.command.CommandService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/")
public class FrontController extends HttpServlet {
    private TemplateEngine engine;
    private CommandService commandService;
    public static Connection CONNECTION;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            CONNECTION = DriverManager.getConnection(
                    StorageConstants.DB_URL,
                    StorageConstants.DB_USERNAME,
                    StorageConstants.DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        new DatabaseInitService().initDb(
                StorageConstants.DB_URL,
                StorageConstants.DB_USERNAME,
                StorageConstants.DB_PASSWORD
        );

        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext().getRealPath("") + "templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);

        commandService = new CommandService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commandService.process(req, resp, engine);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commandService.process(req, resp, engine);
    }
}