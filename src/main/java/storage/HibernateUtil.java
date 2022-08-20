package storage;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import tables.company.Company;
import tables.customer.Customer;
import tables.developer.Developer;
import tables.project.Project;
import tables.skill.Skill;

public class HibernateUtil {
    private static final HibernateUtil INSTANCE;

    @Getter
    private final SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateUtil();
    }

    private HibernateUtil() {
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.url", StorageConstants.DB_URL)
                .setProperty("hibernate.connection.username", StorageConstants.DB_USERNAME)
                .setProperty("hibernate.connection.password", StorageConstants.DB_PASSWORD)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Skill.class)
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Project.class)
                .buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}