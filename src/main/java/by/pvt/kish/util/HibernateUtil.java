package by.pvt.kish.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author Kish Alexey
 */
public class HibernateUtil {
    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    private SessionFactory sessionFactory = null;
    private final ThreadLocal sessions = new ThreadLocal();
    private static HibernateUtil util = null;

    private HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.setNamingStrategy(new CustomNamingStrategy());
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            logger.error("Initial session factory creating failed" + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public Session getSession() {
        Session session = (Session) sessions.get();
        if (session == null) {
            session = sessionFactory.openSession();
            sessions.set(session);
        }
        return session;
    }

    public static HibernateUtil getUtil() {
        if (util == null) {
            util = new HibernateUtil();
        }
        return util;
    }
}
