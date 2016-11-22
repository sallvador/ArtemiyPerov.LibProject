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
            return;
        }
        else {
            Session sess = startSessAndTransaction();
            AuthorsEntity ent = new AuthorsEntity(name);
            saveChanges(sess);
        }
    }

    public List<AuthorsEntity> searchByName(String name){
        Session sess = startSessAndTransaction();
        List<AuthorsEntity> result = new ArrayList<AuthorsEntity>();
        List<AuthorsEntity> authors = sess.createQuery("from AuthorsEntity order by id").list();
        name = name.trim().replaceAll("[\\s]{2,}", " ");
        String[] patterns = name.split(" ");
        for (AuthorsEntity auth : authors){
            boolean matches = true;
            int index = 0;
            while ((matches == true)&&(index<patterns.length)){
                matches = auth.getAuthorname().contains(patterns[index]);
                index++;
            }
            if (matches == true)
                result.add(auth);
        }
        saveChanges(sess);
        return result;
    }

    public AuthorsEntity searchById(long id){
        Session sess = startSessAndTransaction();
        AuthorsEntity result = (AuthorsEntity) sess.createQuery("from AuthorsEntity where (id = "+(new Long(id)).toString()+")").uniqueResult();
        saveChanges(sess);
        return result;
    }
}
