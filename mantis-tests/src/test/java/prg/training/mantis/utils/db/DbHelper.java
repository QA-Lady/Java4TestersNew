package prg.training.mantis.utils.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import prg.training.mantis.model.Issues;
import prg.training.mantis.model.IssuesData;
import prg.training.mantis.model.UserData;
import prg.training.mantis.model.Users;

import java.util.List;

/**
 * Created by QA Lady on 5/19/2017.
 */
public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Users users() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserData> result = session.createQuery("from UserData").list();
        session.getTransaction().commit();
        session.close();

        return new Users(result);
    }

    public Issues issues() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<IssuesData> result = session.createQuery("from IssuesData").list();
        session.getTransaction().commit();
        session.close();

        return new Issues(result);
    }

}
