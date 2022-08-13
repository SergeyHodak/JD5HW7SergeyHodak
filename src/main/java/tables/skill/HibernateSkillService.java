package tables.skill;

import org.hibernate.Session;
import org.hibernate.Transaction;
import storage.HibernateUtil;

import java.util.List;

public class HibernateSkillService implements SkillService {
    private static final HibernateSkillService INSTANCE;

    static {
        INSTANCE = new HibernateSkillService();
    }

    public static HibernateSkillService getInstance() {
        return INSTANCE;
    }

    @Override
    public long create(Skill skill) {
        Session session = openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(skill);
            transaction.commit();
        session.close();
        return skill.getId();
    }

    @Override
    public Skill getById(long id) {
        try(Session session = openSession()) {
            return session.get(Skill.class, id);
        }
    }

    @Override
    public List<Skill> getAll() {
        try(Session session = openSession()) {
            return session.createQuery("FROM Skill", Skill.class).list();
        }
    }

    @Override
    public String update(Skill skill) {
        try {
            Session session = openSession();
                Transaction transaction = session.beginTransaction();
                    session.merge(skill);
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