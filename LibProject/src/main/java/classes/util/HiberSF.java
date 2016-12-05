package classes.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

/**
 * Created by demon on 15.11.2016.
 */
public class HiberSF {
    public static SessionFactory getSessionFactory() {
        Locale.setDefault(Locale.ENGLISH);
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new Configuration().configure()
                    .buildSessionFactory();

        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed. " + e);
            throw new ExceptionInInitializerError(e);
        }
        return sessionFactory;
    }

    public static void killSF(SessionFactory sessionFactory){
        sessionFactory.close();
    }
}
