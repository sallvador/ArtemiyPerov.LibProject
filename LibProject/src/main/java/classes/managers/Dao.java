package classes.managers;

import org.hibernate.Session;
import classes.util.HiberSF;

/**
 * Created by demon on 16.11.2016.
 */
public abstract class Dao {
    static protected Session startSessAndTransaction(){
        Session sess = HiberSF.getSessionFactory().openSession();
        sess.beginTransaction();
        return sess;
    }

    static protected void saveChanges(Session sess){
        sess.flush();
        sess.getTransaction().commit();
        sess.close();
    }
}
