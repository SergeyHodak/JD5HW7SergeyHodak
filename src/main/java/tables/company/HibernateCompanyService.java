package tables.company;

import org.hibernate.Session;
import org.hibernate.Transaction;
import storage.HibernateUtil;

import java.util.List;

public class HibernateCompanyService implements CompanyService {
    private static final HibernateCompanyService INSTANCE;

    static {
        INSTANCE = new HibernateCompanyService();
    }

    public static HibernateCompanyService getInstance() {
        return INSTANCE;
    }

    @Override
    public long create(Company company) {
        Session session = openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(company);
            transaction.commit();
        session.close();
        return company.getId();
    }

    @Override
    public Company getById(long id) {
        try(Session session = openSession()) {
            return session.get(Company.class, id);
        }
    }

    @Override
    public List<Company> getAll() {
        try(Session session = openSession()) {
            return session.createQuery("FROM Company", Company.class).list();
        }
    }

    @Override
    public String update(Company company) {
        try {
            Session session = openSession();
                Transaction transaction = session.beginTransaction();
                    session.merge(company);
                transaction.commit();
            session.close();
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