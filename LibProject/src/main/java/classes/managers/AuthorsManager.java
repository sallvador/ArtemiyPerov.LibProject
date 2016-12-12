package classes.managers;

import classes.entities.AuthorsEntity;
import classes.util.Assistant;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by demon on 16.11.2016.
 */
public class AuthorsManager extends AuthorsDao {

    public void addByObject(AuthorsEntity auth) {
        Session session = GetSessionWithTransaction();
        session.save(auth);
        CommitTransactionCloseSession(session);
    }

    public void removeByObject(AuthorsEntity auth) {
        Session session = GetSessionWithTransaction();
        session.delete(auth);
        CommitTransactionCloseSession(session);
    }

    public void mergeByObject(AuthorsEntity auth) {
        Session session = GetSessionWithTransaction();
        session.merge(auth);
        CommitTransactionCloseSession(session);
    }

    public void addByName(String name) {
        if (name.length() > Assistant.maxTextLength) {
            System.out.println("Name length must be not more than 30 characters");
            return;
        }
        else {
            AuthorsEntity author = new AuthorsEntity(name);
            addByObject(author);
        }
    }

    public List<AuthorsEntity> GetAuthorsByName(String name){
        Session session = GetSessionWithTransaction();
        List<AuthorsEntity> Authors = new ArrayList<AuthorsEntity>();
        Query query = session.createQuery("from AuthorsEntity order by id");
        List<AuthorsEntity> authors = query.list();
        name = Assistant.deleteNeedlessSpaces(name);
        String[] patterns = name.split(" ");
        for (AuthorsEntity auth : authors){
            boolean matches = true;
            int index = 0;
            while ((matches == true)&&(index<patterns.length)){
                matches = auth.getAuthorname().contains(patterns[index]);
                index++;
            }
            if (matches == true)
                Authors.add(auth);
        }
        CommitTransactionCloseSession(session);
        return Authors;
    }

    public AuthorsEntity GetAuthorById(long id){
        Session session = GetSessionWithTransaction();
        Query query = session.createQuery("from AuthorsEntity where (id = :givenId)").setLong("givenId", id);
        AuthorsEntity Author = (AuthorsEntity) query.uniqueResult();
        CommitTransactionCloseSession(session);
        return Author;
    }

    public AuthorsEntity GetAuthorByExactName(String name){
        AuthorsEntity author = null;
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        ProcedureCall pc = null;
        Query query = session.createQuery("from AuthorsEntity where (LOWER(authorname) = :name)").setString("name", name.toLowerCase());
        author = (AuthorsEntity) query.uniqueResult();
        return author;
    }
}
