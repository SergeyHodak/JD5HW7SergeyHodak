package tables.developer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import storage.HibernateUtil;
import tables.skill.Skill;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateDeveloperService implements DeveloperService {
    private static final HibernateDeveloperService INSTANCE;

    static {
        INSTANCE = new HibernateDeveloperService();
    }

    public static HibernateDeveloperService getInstance() {
        return INSTANCE;
    }

    @Override
    public long create(Developer developer, long[] skillIds) {
        Session session = openSession();
            Transaction transaction = session.beginTransaction();
                Set<Skill> skills = new HashSet<>();
                for (long skillId : skillIds) {
                    skills.add(session.get(Skill.class, skillId));
                }
                developer.setSkills(skills);
                session.persist(developer);
            transaction.commit();
        session.close();
        return developer.getId();
    }

    @Override
    public Developer getById(long id) {
        try(Session session = openSession()) {
            Developer developer = session.get(Developer.class, id);
            for (Skill skill : developer.getSkills()) {}
            return developer;
        }
    }

    @Override
    public List<Developer> getAll() {
        try(Session session = openSession()) {
            List<Developer> developers = session.createQuery("FROM Developer", Developer.class).list();
            for (Developer developer : developers) {
                for (Skill skill : developer.getSkills()) {}
            }
            return developers;
        }
    }

    @Override
    public String update(Developer developer, long[] skillIds) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
                Set<Skill> skills = new HashSet<>();
                for (long skillId : skillIds) {
                    skills.add(session.get(Skill.class, skillId));
                }
                developer.setSkills(skills);
                session.merge(developer);
            transaction.commit();
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

    @Override
    public List<Developer> getDevelopersByDepartment(String department) {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT T1.*" + '\n' +
                            "FROM developer AS T1" + '\n' +
                            "JOIN developer_skill AS T2 ON T1.id=T2.developer_id" + '\n' +
                            "JOIN skill AS T3 ON T2.skill_id=T3.id" + '\n' +
                            "GROUP BY T3.department, T1.id" + '\n' +
                            "HAVING T3.department = :department",
                    Developer.class
            );
            nativeQuery.setParameter("department", department);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                for (Skill skill : developer.getSkills()) {}
            }
            return developers;
        }
    }

    @Override
    public List<Developer> getDevelopersBySkillLevel(String skillLevel) {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT T1.*" + '\n' +
                            "FROM developer AS T1" + '\n' +
                            "JOIN developer_skill AS T2 ON T1.id=T2.developer_id" + '\n' +
                            "JOIN skill AS T3 ON T2.skill_id=T3.id" + '\n' +
                            "GROUP BY T3.skill_level , T1.id" + '\n' +
                            "HAVING T3.skill_level = :skillLevel",
                    Developer.class
            );
            nativeQuery.setParameter("skillLevel", skillLevel);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                for (Skill skill : developer.getSkills()) {}
            }
            return developers;
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}