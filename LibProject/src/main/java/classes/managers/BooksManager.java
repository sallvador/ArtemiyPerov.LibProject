package classes.managers;

import classes.entities.BooksEntity;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by demon on 16.11.2016.
 */
public class BooksManager extends BooksDao{
    public void addByObject(BooksEntity book) {
        Session session = GetSessionWithTransaction();
        session.save(book);
        CommitTransactionCloseSession(session);
    }

    public void removeByObject(BooksEntity book) {
        Session session = GetSessionWithTransaction();
        session.delete(book);
        CommitTransactionCloseSession(session);
    }

    public void mergeByObject(BooksEntity book) {
        Session session = GetSessionWithTransaction();
        session.merge(book);
        CommitTransactionCloseSession(session);
    }

    public List<BooksEntity> getBooksByPattern(String pattern){
        List<BooksEntity> books = new ArrayList<BooksEntity>();
        String[] patterns = pattern.split(" ");
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select b.id || ',' || b.bookname || ' ' || a.authorname from BooksEntity b, AuthorsEntity a where (b.authorid=a.authorid) and (LOWER(b.bookname || a.authorname) like '%'||:pattern||'%')").setString("pattern", patterns[0].toLowerCase());
        List<String> preResult = query.list();
        session.getTransaction().commit();
        session.close();

        for (String string: preResult){
            boolean matches = true;
            if (patterns.length > 1) {
                int index = 1;
                while ((matches == true) && (index < patterns.length)) {
                    matches = string.toLowerCase().contains(patterns[index].toLowerCase());
                    index++;
                }
            }
            if (matches == true){
                string = string.split(",")[0];
                session = sf.openSession();
                session.beginTransaction();
                query = session.createQuery("from BooksEntity where (bookid = :id)").setLong("id", new Long(string));
                books.add((BooksEntity) query.uniqueResult());
                session.getTransaction().commit();
                session.close();
                sf.close();
            }

        }


        return books;
    }
}
