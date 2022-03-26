package dao.tools;

import org.hibernate.SessionFactory;

import java.io.File;
import org.hibernate.cfg.Configuration;

public class HibernateSessionUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initSessionFactory();
        }

        return sessionFactory;
    }
}
