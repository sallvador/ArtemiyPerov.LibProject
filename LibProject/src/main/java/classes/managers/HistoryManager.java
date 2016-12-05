package classes.managers;

import classes.entities.HistoryEntity;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by demon on 05.12.2016.
 */
public class HistoryManager extends HistoryDao{
    public void addByObject(HistoryEntity hUnit) {
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(hUnit);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }

    public void removeByObject(HistoryEntity hUnit) {
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(hUnit);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }

    public void mergeByObject(HistoryEntity hUnit) {
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.merge(hUnit);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }

    public List<HistoryEntity> searchHistoryByUsersId(long userID){
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from HistoryEntity where userid = :userid").setLong("userid",userID);
        List<HistoryEntity> history = query.list();
        return history;
    }
}
