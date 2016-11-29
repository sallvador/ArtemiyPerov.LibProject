package classes.managers;

import org.hibernate.Session;
import classes.util.HiberSF;

/**
 * Created by demon on 16.11.2016.
 */
public abstract class Dao {
    static protected Session GetSessionWithTransaction(){
        Session session = HiberSF.getSessionFactory().openSession();
        session.flush();
        session.beginTransaction();
        return session;
    }

    static protected void CommitTransactionCloseSession(Session session){
        session.flush();
        session.getTransaction().commit();
        session.close();
    }
}
