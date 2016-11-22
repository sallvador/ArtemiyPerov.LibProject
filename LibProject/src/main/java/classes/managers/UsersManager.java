package classes.managers;

import classes.entities.AuthorsEntity;
import classes.entities.UsersEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 16.11.2016.
 */
public class UsersManager extends UsersDao{
    public void addByObject(UsersEntity user) {
        Session sess = startSessAndTransaction();
        sess.save(user);
        saveChanges(sess);
    }

    public void removeByObject(UsersEntity user) {
        Session sess = startSessAndTransaction();
        sess.delete(user);
        saveChanges(sess);
    }

    public void mergeByObject(UsersEntity user) {
        Session sess = startSessAndTransaction();
        sess.merge(user);
        saveChanges(sess);
    }

    public UsersEntity searchByEmail(String email){
        UsersEntity result = null;
        email = email.trim();
        Pattern pattern = Pattern.compile("^([A-Za-z0-9]{1}[A-Za-z0-9-_.]{0,20}[A-Za-z0-9]{1})@([A-Za-z0-9][a-zA-Z0-9-]*[A-Za-z0-9].)+(ru|com|net|org)$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == false){
            System.out.println("Wrong email format");
            return null;
        }
        Session sess = startSessAndTransaction();
        result = (UsersEntity) sess.createQuery("from UsersEntity where (email = '" + email + "')").uniqueResult();
        saveChanges(sess);
        return result;
    }

    public List<UsersEntity> searchByLastname(String lastname){
        lastname = lastname.trim().replaceAll("[\\s]{2,}", " ");
        if (lastname.length() > 30){
            System.out.println("Surname must be not longer than 30 characters");
            return null;
        }
        List<UsersEntity> result = new ArrayList<UsersEntity>();
        Session sess = startSessAndTransaction();
        List<UsersEntity> authors = sess.createQuery("from UsersEntity order by id").list();
        String[] patterns = lastname.split(" ");
        for (UsersEntity user : authors){
            boolean matches = true;
            int index = 0;
            while ((matches == true)&&(index<patterns.length)){
                matches = user.getLastname().contains(patterns[index]);
                index++;
            }
            if (matches == true)
                result.add(user);
        }
        saveChanges(sess);
        return result;
    }
}
