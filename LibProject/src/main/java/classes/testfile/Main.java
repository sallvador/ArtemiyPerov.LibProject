package classes.testfile;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import classes.util.HiberSF;
import java.util.Locale;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Created by demon on 15.11.2016.
 */
public class Main {

    public static void main(final String[] args) throws Exception {

        System.out.println("testing hiber");
        Locale.setDefault(Locale.ENGLISH);
        Session sess = HiberSF.getSessionFactory().openSession();

        sess.close();
        exit(-1);

    }
}