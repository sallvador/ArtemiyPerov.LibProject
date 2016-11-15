package classes.managers;

import classes.entities.AuthorsEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by demon on 16.11.2016.
 */
public class AuthorsManager extends AuthorsDao {
    static long lastID = 0;

    public void addByObject(AuthorsEntity auth) {
        Session sess = startSessAndTransaction();
        sess.save(auth);
        saveChanges(sess);
    }

    public void removeByObject(AuthorsEntity auth) {
        Session sess = startSessAndTransaction();
        sess.delete(auth);
        saveChanges(sess);
    }

    public void mergeByObject(AuthorsEntity auth) {
        Session sess = startSessAndTransaction();
        sess.merge(auth);
        saveChanges(sess);
    }

    public void addByName(String name) {
        if (name.length() > 30) {
            System.out.println("Name length must be not more than 30 characters");
        }
        else {
            Session sess = startSessAndTransaction();
            AuthorsEntity ent = new AuthorsEntity();
            ent.setAuthorname(name);
        }
    }

    public List<AuthorsEntity> searchByName(String name){
        Session sess = startSessAndTransaction();
        List<AuthorsEntity> result = new ArrayList<AuthorsEntity>();
        List<AuthorsEntity> authors = sess.createQuery("from AuthorsEntity order by id").list();
        for (AuthorsEntity auth : authors){
            if (auth.getAuthorname().contains(name))
                result.add(auth);
        }
        return result;
    }
    public AuthorsEntity searchById(long id){
        Session sess = startSessAndTransaction();
        return (AuthorsEntity) sess.createQuery("from AuthorsEntity where (id = "+(new Long(id)).toString()+")").uniqueResult();
    }
}
