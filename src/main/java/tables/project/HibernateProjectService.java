package tables.project;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import storage.HibernateUtil;
import tables.company.Company;
import tables.customer.Customer;
import tables.developer.Developer;

import java.util.*;

public class HibernateProjectService implements ProjectService {
    private static final HibernateProjectService INSTANCE;

    static {
        INSTANCE = new HibernateProjectService();
    }

    public static HibernateProjectService getInstance() {
        return INSTANCE;
    }

    @Override
    public long create(Project project, long companyId, long customerId, long[] developerIds) {
        Session session = openSession();
            Transaction transaction = session.beginTransaction();
                Company company = session.get(Company.class, companyId);
                project.setCompany(company);
                Customer customer = session.get(Customer.class, customerId);
                project.setCustomer(customer);
                Set<Developer> developers = new HashSet<>();
                for (long developerId : developerIds) {
                    developers.add(session.get(Developer.class, developerId));
                }
                project.setDevelopers(developers);
                session.persist(project);
            transaction.commit();
        session.close();
        long id = project.getId();
        updateCostByProjectId(id);
        return id;
    }

    private void updateCostByProjectId(long projectId) {
        try(Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
                String sql =
                    "UPDATE project AS T1" + '\n' +
                    "SET T1.cost = (" + '\n' +
                    "    SELECT SUM(salary)" + '\n' +
                    "    FROM developer AS T2" + '\n' +
                    "    WHERE T2.id IN (" + '\n' +
                    "        SELECT T3.developer_id" + '\n' +
                    "        FROM project_developer AS T3" + '\n' +
                    "        WHERE T3.project_id=T1.id" + '\n' +
                    "    )" + '\n' +
                    ")" +
                    "WHERE id = ?";
                NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class);
                query.setParameter(1, projectId);
                query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public Project getById(long id) {
        try(Session session = openSession()) {
            Project project = session.get(Project.class, id);
            for (Developer developer : project.getDevelopers()) {}
            return project;
        }
    }

    @Override
    public double getCostById(long id) {
        String sql = "SELECT cost FROM project WHERE id = ?";
        try(Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
                NativeQuery<Double> query = session.createNativeQuery(sql, Double.class);
                query.setParameter(1, id);
                Double singleResult = query.getSingleResult();
            transaction.commit();
            return singleResult;
        }
    }

    @Override
    public Set<Developer> getDevelopersByProjectId(long id) {
        try(Session session = openSession()) {
            Project project = session.get(Project.class, id);
            for (Developer developer : project.getDevelopers()) {}
            return project.getDevelopers();
        }
    }

    @Override
    public List<Project> getAll() {
        try(Session session = openSession()) {
            List<Project> projects = session.createQuery("FROM Project", Project.class).list();
            for (Project project : projects) {
                for (Developer developer : project.getDevelopers()) {}
            }
            return projects;
        }
    }

    @Override
    public List<ProjectSpecialFormat> getAllBySpecialFormat() {
        try(Session session = openSession()) {
            List<Project> projects = session.createQuery("FROM Project", Project.class).list();
            List<ProjectSpecialFormat> result = new ArrayList<>();
            for (Project project : projects) {
                ProjectSpecialFormat projectSpecialFormat = new ProjectSpecialFormat();
                projectSpecialFormat.setCreationDate(project.getCreationDate());
                projectSpecialFormat.setName(project.getName());
                int count = 0;
                for (Developer developer : project.getDevelopers()) {
                    count++;
                }
                projectSpecialFormat.setDeveloperCount(count);
                result.add(projectSpecialFormat);
            }
            return result;
        }
    }

    @Override
    public String update(Project project, long companyId, long customerId, long[] developerIds) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
                Company company = session.get(Company.class, companyId);
                project.setCompany(company);
                Customer customer = session.get(Customer.class, customerId);
                project.setCustomer(customer);
                Set<Developer> developers = new HashSet<>();
                for (long developerId : developerIds) {
                    developers.add(session.get(Developer.class, developerId));
                }
                project.setDevelopers(developers);
                session.merge(project);
            transaction.commit();
            long id = project.getId();
            updateCostByProjectId(id);
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteById(long id) {
        try {
            Session session = openSession();
                Transaction transaction = session.beginTransaction();
                    session.remove(getById(id));
                transaction.commit();
            session.close();
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}