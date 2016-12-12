package classes.managers;

import classes.entities.UsersEntity;
import classes.util.Assistant;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 16.11.2016.
 */
public class UsersManager extends UsersDao{
    public void addByObject(UsersEntity user) {
        Session session = GetSessionWithTransaction();
        session.save(user);
        CommitTransactionCloseSession(session);
    }

    public void removeByObject(UsersEntity user) {
        Session session = GetSessionWithTransaction();
        session.delete(user);
        CommitTransactionCloseSession(session);
    }

    public void mergeByObject(UsersEntity user) {
        Session session = GetSessionWithTransaction();
        session.merge(user);
        CommitTransactionCloseSession(session);
    }

    public UsersEntity GetUserByPhone(String phone){
        UsersEntity user = null;
        Matcher matcher = Assistant.phonePattern.matcher(phone);
        if (matcher.matches() == false){
            System.out.println("Wrong email format");
            return null;
        }
        phone = "+" + phone;
        Session session = GetSessionWithTransaction();
        Query query = session.createQuery("from UsersEntity where (phone = :phone)").setString("phone", phone);
        user = (UsersEntity) query.uniqueResult();
        CommitTransactionCloseSession(session);
        return user;
    }

    public UsersEntity GetUserByEmail(String email){
        UsersEntity User = null;
        email = email.trim();
        Matcher matcher = Assistant.emailPattern.matcher(email);
        if (matcher.matches() == false){
            System.out.println("Wrong email format");
            return null;
        }
        Session session = GetSessionWithTransaction();
        Query query = session.createQuery("from UsersEntity where (email = :email)").setString("email", email);
        User = (UsersEntity) query.uniqueResult();
        CommitTransactionCloseSession(session);
        return User;
    }

    public List<UsersEntity> GetUserByLastname(String lastName){
        lastName = Assistant.deleteNeedlessSpaces(lastName);
        if (lastName.length() > Assistant.maxTextLength){
            System.out.println("Surname must be not longer than 30 characters");
            return null;
        }
        List<UsersEntity> Users = new ArrayList<UsersEntity>();
        Session session = GetSessionWithTransaction();
        Query query = session.createQuery("from UsersEntity order by id");
        List<UsersEntity> authors = query.list();
        String[] patterns = lastName.split(" ");
        for (UsersEntity user : authors){
            boolean matches = true;
            int index = 0;
            while ((matches == true)&&(index<patterns.length)){
                matches = user.getLastname().contains(patterns[index]);
                index++;
            }
            if (matches == true)
                Users.add(user);
        }
        CommitTransactionCloseSession(session);
        return Users;
    }

    public UsersEntity getUserByID(long ID){
        UsersEntity user = null;
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from UsersEntity where userid = :userid").setLong("userid", ID);
        user = (UsersEntity) query.uniqueResult();
        return user;
    }
}
